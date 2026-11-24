package minero.modelo;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GeneradorPorNivelTest {

    @Test
    public void testNivel1SoloSumasYRestas() {
        GeneradorPorNivel generador = new GeneradorPorNivel(1);
        Set<Class<?>> tipos = new HashSet<>();

        for (int i = 0; i < 200; i++) {
            Pregunta p = generador.generar();
            tipos.add(p.getClass());
            int resultado = p.calcularResultado();
            assertTrue(resultado >= 0 && resultado <= 40);
        }

        assertEquals(Set.of(PreguntaSuma.class, PreguntaResta.class), tipos);
    }

    @Test
    public void testNivel2SumasRestasYTablas() {
        GeneradorPorNivel generador = new GeneradorPorNivel(2);
        Set<Class<?>> tipos = new HashSet<>();

        for (int i = 0; i < 250; i++) {
            Pregunta p = generador.generar();
            tipos.add(p.getClass());
            int resultado = p.calcularResultado();
            assertTrue(resultado >= 0 && resultado <= 100);
        }

        assertEquals(Set.of(PreguntaSuma.class, PreguntaResta.class, PreguntaMultiplicacion.class), tipos);
    }

    @Test
    public void testNivel3MultiplicacionesYDivisiones() {
        GeneradorPorNivel generador = new GeneradorPorNivel(3);
        Set<Class<?>> tipos = new HashSet<>();

        for (int i = 0; i < 200; i++) {
            tipos.add(generador.generar().getClass());
        }

        assertEquals(Set.of(PreguntaMultiplicacion.class, PreguntaDivision.class), tipos);
    }

    @Test
    public void testNivel4OperacionesCombinadas() {
        GeneradorPorNivel generador = new GeneradorPorNivel(4);

        for (int i = 0; i < 50; i++) {
            Pregunta p = generador.generar();
            assertTrue(p instanceof PreguntaCombinada);
        }
    }

    @Test
    public void testNivelInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new GeneradorPorNivel(0));
        assertThrows(IllegalArgumentException.class, () -> new GeneradorPorNivel(5));
        assertEquals(3, new GeneradorPorNivel(3).getNivel());
    }
}
