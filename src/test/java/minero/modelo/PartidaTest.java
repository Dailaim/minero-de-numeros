package minero.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PartidaTest {

    private Partida partida;

    @BeforeEach
    public void setUp() {
        GeneradorPreguntas generadorFijo = () -> new PreguntaSuma(2, 3);
        List<Nivel> niveles = List.of(
                new Nivel(1, 2, Gema.ORO, generadorFijo),
                new Nivel(2, 1, Gema.ESMERALDA, generadorFijo)
        );
        partida = new Partida(new Jugador("Laura"), niveles, new CalculadorPuntaje());
    }

    @Test
    public void testEstadoInicialDeLaPartida() {
        assertEquals(EstadoPartida.EN_CURSO, partida.getEstado());
        assertFalse(partida.estaTerminada());
        assertEquals(1, partida.getNivelActual().getNumero());
        assertEquals(3, partida.getJugador().getVidas());
    }

    @Test
    public void testRespuestaCorrectaSumaPuntos() {
        assertTrue(partida.responder(5, 12));
        // 100 base + 12 * 5 = 160
        assertEquals(160, partida.getJugador().getPuntos());
        assertEquals(1, partida.getNivelActual().getPreguntasResueltas());
    }

    @Test
    public void testCompletarNivelEntregaGemaYBono() {
        partida.responder(5, 0);
        partida.responder(5, 0);

        assertEquals(List.of(Gema.ORO), partida.getJugador().getGemas());
        assertEquals(700, partida.getJugador().getPuntos());
        assertEquals(2, partida.getNivelActual().getNumero());
    }

    @Test
    public void testVictoriaConBonoPorVidas() {
        partida.responder(5, 20);
        partida.responder(5, 20);
        partida.responder(5, 20);

        assertEquals(EstadoPartida.VICTORIA, partida.getEstado());
        // 3*200 + 500 + 1000 + 3*200 = 2700
        assertEquals(2700, partida.getJugador().getPuntos());
        assertEquals(List.of(Gema.ORO, Gema.ESMERALDA), partida.getJugador().getGemas());
    }

    @Test
    public void testVidasSeConservanEntreNiveles() {
        partida.responder(99, 10); // pierde 1 vida
        partida.responder(5, 0);
        partida.responder(5, 0);
        assertEquals(2, partida.getJugador().getVidas());

        partida.responder(5, 0);
        assertEquals(EstadoPartida.VICTORIA, partida.getEstado());
        assertEquals(300 + 500 + 1000 + 400, partida.getJugador().getPuntos());
    }

    @Test
    public void testErrorYTiempoAgotadoQuitanVidas() {
        Pregunta antes = partida.getPreguntaActual();
        assertFalse(partida.responder(99, 15));
        assertEquals(2, partida.getJugador().getVidas());
        assertEquals(0, partida.getJugador().getPuntos());
        assertNotSame(antes, partida.getPreguntaActual());

        Pregunta antesTiempo = partida.getPreguntaActual();
        partida.agotarTiempo();
        assertEquals(1, partida.getJugador().getVidas());
        assertNotSame(antesTiempo, partida.getPreguntaActual());
    }

    @Test
    public void testDerrotaAlQuedarseSinVidas() {
        partida.responder(5, 10);
        partida.responder(99, 10);
        partida.agotarTiempo();
        partida.responder(99, 10);

        assertEquals(EstadoPartida.DERROTA, partida.getEstado());
        assertTrue(partida.estaTerminada());
        assertEquals(150, partida.getJugador().getPuntos());

        assertThrows(IllegalStateException.class, () -> partida.responder(5, 10));
        assertThrows(IllegalStateException.class, () -> partida.agotarTiempo());
    }

    @Test
    public void testCrearRegistroSoloAlTerminar() {
        assertThrows(IllegalStateException.class, () -> partida.crearRegistro());

        partida.responder(5, 20);
        partida.responder(5, 20);
        partida.responder(5, 20);

        RegistroPuntaje registro = partida.crearRegistro();
        assertEquals(new RegistroPuntaje("Laura", 2700, 2), registro);
    }

    @Test
    public void testPartidaCompletaConCuatroNiveles() {
        Partida completa = Partida.nueva("Carlos");
        assertEquals(4, completa.getCantidadNiveles());

        int[] preguntasEsperadas = {6, 5, 4, 3};
        Gema[] gemasEsperadas = {Gema.ORO, Gema.ESMERALDA, Gema.RUBI, Gema.DIAMANTE};
        List<Nivel> niveles = completa.getNiveles();
        for (int i = 0; i < 4; i++) {
            assertEquals(i + 1, niveles.get(i).getNumero());
            assertEquals(preguntasEsperadas[i], niveles.get(i).getCantidadPreguntas());
            assertEquals(gemasEsperadas[i], niveles.get(i).getRecompensa());
        }

        while (!completa.estaTerminada()) {
            int respuesta = completa.getPreguntaActual().calcularResultado();
            completa.responder(respuesta, 10);
        }

        assertEquals(EstadoPartida.VICTORIA, completa.getEstado());
        // 18 preguntas * 150 + bonos (500+1000+1500+2000) + 3 vidas * 200
        assertEquals(18 * 150 + 5000 + 600, completa.getJugador().getPuntos());
        assertEquals(4, completa.getJugador().getGemas().size());
    }

    @Test
    public void testValidaciones() {
        assertThrows(IllegalArgumentException.class, () -> partida.responder(5, 25));
        assertThrows(IllegalArgumentException.class,
                () -> new Partida(new Jugador("Laura"), List.of(), new CalculadorPuntaje()));
    }
}
