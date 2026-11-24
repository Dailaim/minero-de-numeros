package minero.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NivelTest {

    private Nivel nivel;

    @BeforeEach
    public void setUp() {
        // Generador fijo (2 + 3 = 5) para poder probar facil
        GeneradorPreguntas generadorFijo = () -> new PreguntaSuma(2, 3);
        nivel = new Nivel(1, 3, Gema.ORO, generadorFijo);
    }

    @Test
    public void testSoloPrimeraPreguntaDesbloqueadaAlInicio() {
        assertTrue(nivel.estaDesbloqueada(0));
        assertFalse(nivel.estaDesbloqueada(1));
        assertFalse(nivel.estaDesbloqueada(2));
        assertEquals(0, nivel.getPreguntasResueltas());
    }

    @Test
    public void testResponderBienDesbloqueaLaSiguiente() {
        assertTrue(nivel.responder(5));

        assertTrue(nivel.estaResuelta(0));
        assertTrue(nivel.estaDesbloqueada(1));
        assertFalse(nivel.estaDesbloqueada(2));
        assertEquals(1, nivel.getPreguntasResueltas());
    }

    @Test
    public void testResponderMalReemplazaPregunta() {
        Pregunta anterior = nivel.getPreguntaActual();
        assertFalse(nivel.responder(99));

        assertNotSame(anterior, nivel.getPreguntaActual());
        assertEquals(0, nivel.getPreguntasResueltas());
    }

    @Test
    public void testRegistrarFalloPorTiempo() {
        Pregunta anterior = nivel.getPreguntaActual();
        nivel.registrarFallo();

        assertNotSame(anterior, nivel.getPreguntaActual());
        assertEquals(0, nivel.getPreguntasResueltas());
    }

    @Test
    public void testCompletarNivel() {
        nivel.responder(5);
        nivel.responder(5);
        assertFalse(nivel.estaCompletado());

        nivel.responder(5);
        assertTrue(nivel.estaCompletado());

        assertThrows(IllegalStateException.class, () -> nivel.getPreguntaActual());
        assertThrows(IllegalStateException.class, () -> nivel.responder(5));
    }

    @Test
    public void testDatosYValidacionesDelNivel() {
        assertEquals(1, nivel.getNumero());
        assertEquals(Gema.ORO, nivel.getRecompensa());
        assertEquals(3, nivel.getCantidadPreguntas());

        GeneradorPreguntas gen = () -> new PreguntaSuma(1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Nivel(0, 3, Gema.ORO, gen));
        assertThrows(IllegalArgumentException.class, () -> new Nivel(1, 0, Gema.ORO, gen));
        assertThrows(IllegalArgumentException.class, () -> new Nivel(1, 3, null, gen));
        assertThrows(IllegalArgumentException.class, () -> new Nivel(1, 3, Gema.ORO, null));
    }
}
