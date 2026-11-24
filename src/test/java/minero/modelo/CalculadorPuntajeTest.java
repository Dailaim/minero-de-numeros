package minero.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadorPuntajeTest {

    @Test
    public void testPuntosPorRespuesta() {
        CalculadorPuntaje calc = new CalculadorPuntaje();
        assertEquals(100, calc.puntosPorRespuesta(0));
        assertEquals(150, calc.puntosPorRespuesta(10));
        assertEquals(200, calc.puntosPorRespuesta(20));

        assertThrows(IllegalArgumentException.class, () -> calc.puntosPorRespuesta(-1));
        assertThrows(IllegalArgumentException.class, () -> calc.puntosPorRespuesta(25));
    }

    @Test
    public void testBonoPorNivel() {
        CalculadorPuntaje calc = new CalculadorPuntaje();
        assertEquals(500, calc.bonoPorNivel(1));
        assertEquals(1500, calc.bonoPorNivel(3));
        assertEquals(2000, calc.bonoPorNivel(4));

        assertThrows(IllegalArgumentException.class, () -> calc.bonoPorNivel(0));
    }

    @Test
    public void testBonoPorVidas() {
        CalculadorPuntaje calc = new CalculadorPuntaje();
        assertEquals(0, calc.bonoPorVidas(0));
        assertEquals(400, calc.bonoPorVidas(2));
        assertEquals(600, calc.bonoPorVidas(3));

        assertThrows(IllegalArgumentException.class, () -> calc.bonoPorVidas(-1));
    }
}
