package minero.modelo;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PreguntaTest {

    @Test
    public void testPreguntaSuma() {
        PreguntaSuma pregunta = new PreguntaSuma(14, 7);
        assertEquals(21, pregunta.calcularResultado());
        assertEquals("14 + 7 = ?", pregunta.getEnunciado());
        assertTrue(pregunta.esCorrecta(21));
        assertFalse(pregunta.esCorrecta(19));

        assertThrows(IllegalArgumentException.class, () -> new PreguntaSuma(-1, 5));
    }

    @Test
    public void testPreguntaResta() {
        PreguntaResta pregunta = new PreguntaResta(18, 5);
        assertEquals(13, pregunta.calcularResultado());
        assertEquals("18 - 5 = ?", pregunta.getEnunciado());
        assertTrue(pregunta.esCorrecta(13));

        PreguntaResta cero = new PreguntaResta(9, 9);
        assertTrue(cero.esCorrecta(0));

        assertThrows(IllegalArgumentException.class, () -> new PreguntaResta(3, 8));
    }

    @Test
    public void testPreguntaMultiplicacion() {
        PreguntaMultiplicacion pregunta = new PreguntaMultiplicacion(7, 8);
        assertEquals(56, pregunta.calcularResultado());
        assertEquals("7 x 8 = ?", pregunta.getEnunciado());
        assertTrue(pregunta.esCorrecta(56));

        assertThrows(IllegalArgumentException.class, () -> new PreguntaMultiplicacion(4, -2));
    }

    @Test
    public void testPreguntaDivision() {
        PreguntaDivision pregunta = new PreguntaDivision(56, 8);
        assertEquals(7, pregunta.calcularResultado());
        assertEquals("56 / 8 = ?", pregunta.getEnunciado());
        assertTrue(pregunta.esCorrecta(7));

        assertThrows(IllegalArgumentException.class, () -> new PreguntaDivision(10, 3));
        assertThrows(IllegalArgumentException.class, () -> new PreguntaDivision(10, 0));
    }

    @Test
    public void testPreguntaCombinada() {
        // Primero se multiplica 4 x 5 = 20 y luego se suma 3
        PreguntaCombinada pregunta = new PreguntaCombinada(3, 4, 5);
        assertEquals(23, pregunta.calcularResultado());
        assertEquals("3 + 4 x 5 = ?", pregunta.getEnunciado());
        assertTrue(pregunta.esCorrecta(23));
        assertFalse(pregunta.esCorrecta(35));

        assertThrows(IllegalArgumentException.class, () -> new PreguntaCombinada(3, -1, 5));
    }

    @Test
    public void testGeneracionDeOpciones() {
        for (int i = 0; i < 50; i++) {
            Pregunta pregunta = new PreguntaSuma(i % 15, 4);
            List<Integer> opciones = pregunta.getOpciones();

            assertEquals(4, opciones.size());
            assertTrue(opciones.contains(pregunta.calcularResultado()));

            Set<Integer> sinRepetidos = new HashSet<>(opciones);
            assertEquals(4, sinRepetidos.size());

            for (int opcion : opciones) {
                assertTrue(opcion >= 0);
            }
        }
    }

    @Test
    public void testOpcionesCuandoResultadoEsCero() {
        PreguntaResta pregunta = new PreguntaResta(5, 5);
        List<Integer> opciones = pregunta.getOpciones();

        assertEquals(4, opciones.size());
        assertTrue(opciones.contains(0));
        for (int opcion : opciones) {
            assertTrue(opcion >= 0);
        }
    }

    @Test
    public void testPolimorfismoEnListaDePreguntas() {
        List<Pregunta> preguntas = List.of(
                new PreguntaSuma(4, 2),
                new PreguntaResta(4, 2),
                new PreguntaMultiplicacion(4, 2),
                new PreguntaDivision(4, 2),
                new PreguntaCombinada(4, 2, 3)
        );
        int[] respuestas = {6, 2, 8, 2, 10};

        for (int i = 0; i < preguntas.size(); i++) {
            assertTrue(preguntas.get(i).esCorrecta(respuestas[i]));
            assertFalse(preguntas.get(i).esCorrecta(respuestas[i] + 1));
        }
    }
}
