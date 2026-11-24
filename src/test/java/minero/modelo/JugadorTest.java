package minero.modelo;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JugadorTest {

    @Test
    public void testValoresIniciales() {
        Jugador jugador = new Jugador("Carlos");
        assertEquals("Carlos", jugador.getNombre());
        assertEquals(3, jugador.getVidas());
        assertEquals(0, jugador.getPuntos());
        assertTrue(jugador.getGemas().isEmpty());
        assertTrue(jugador.tieneVidas());
    }

    @Test
    public void testPerderVidasNoBajaDeCero() {
        Jugador jugador = new Jugador("Carlos");
        jugador.perderVida();
        jugador.perderVida();
        jugador.perderVida();
        jugador.perderVida();

        assertEquals(0, jugador.getVidas());
        assertFalse(jugador.tieneVidas());
    }

    @Test
    public void testSumarPuntos() {
        Jugador jugador = new Jugador("Sofia");
        jugador.sumarPuntos(150);
        jugador.sumarPuntos(500);

        assertEquals(650, jugador.getPuntos());
        assertThrows(IllegalArgumentException.class, () -> jugador.sumarPuntos(-10));
    }

    @Test
    public void testAgregarGemas() {
        Jugador jugador = new Jugador("Sofia");
        jugador.agregarGema(Gema.ORO);
        jugador.agregarGema(Gema.ESMERALDA);

        assertEquals(List.of(Gema.ORO, Gema.ESMERALDA), jugador.getGemas());
        assertThrows(IllegalArgumentException.class, () -> jugador.agregarGema(null));
    }

    @Test
    public void testValidacionNombre() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador("   "));
        assertThrows(IllegalArgumentException.class, () -> new Jugador(null));
        assertEquals("Mateo", new Jugador("  Mateo ").getNombre());
    }
}
