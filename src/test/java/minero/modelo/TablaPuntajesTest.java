package minero.modelo;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TablaPuntajesTest {

    @Test
    public void testRegistroPuntajeLineaYOrden() {
        RegistroPuntaje registro = new RegistroPuntaje("Carlos", 3400, 4);
        assertEquals("Carlos;3400;4", registro.aLinea());
        assertEquals(registro, RegistroPuntaje.desdeLinea("Carlos;3400;4"));

        RegistroPuntaje conPuntoYComa = new RegistroPuntaje("Carlos;M", 500, 1);
        assertEquals("Carlos,M", conPuntoYComa.getNombre());

        RegistroPuntaje mayor = new RegistroPuntaje("Sofia", 2000, 3);
        RegistroPuntaje empate = new RegistroPuntaje("Mateo", 2000, 1);
        assertTrue(mayor.compareTo(empate) < 0);

        assertThrows(IllegalArgumentException.class, () -> RegistroPuntaje.desdeLinea("invalido"));
        assertThrows(IllegalArgumentException.class, () -> new RegistroPuntaje("", 100, 0));
        assertThrows(IllegalArgumentException.class, () -> new RegistroPuntaje("Sofia", -5, 0));
    }

    @Test
    public void testOrdenarYLimitarRegistros() {
        TablaPuntajes tabla = new TablaPuntajes(2);
        tabla.agregar(new RegistroPuntaje("Mateo", 1000, 1));
        tabla.agregar(new RegistroPuntaje("Sofia", 3000, 4));

        assertFalse(tabla.agregar(new RegistroPuntaje("Carlos", 500, 0)));
        assertTrue(tabla.agregar(new RegistroPuntaje("Laura", 2200, 2)));

        List<RegistroPuntaje> mejores = tabla.getMejores();
        assertEquals(2, mejores.size());
        assertEquals("Sofia", mejores.get(0).getNombre());
        assertEquals("Laura", mejores.get(1).getNombre());
    }

    @Test
    public void testEsMejorPuntaje() {
        TablaPuntajes tabla = new TablaPuntajes(1);
        assertTrue(tabla.esMejorPuntaje(0));

        tabla.agregar(new RegistroPuntaje("Sofia", 800, 1));
        assertTrue(tabla.esMejorPuntaje(850));
        assertFalse(tabla.esMejorPuntaje(800));
    }

    @Test
    public void testGuardarYCargarArchivo() throws IOException {
        Path archivo = Files.createTempFile("puntajes-test", ".txt");
        try {
            TablaPuntajes tabla = new TablaPuntajes();
            tabla.agregar(new RegistroPuntaje("Sofia", 3200, 4));
            tabla.agregar(new RegistroPuntaje("Carlos", 1450, 2));

            tabla.guardar(archivo);
            TablaPuntajes cargada = TablaPuntajes.cargar(archivo);

            assertEquals(tabla.getMejores(), cargada.getMejores());
        } finally {
            Files.deleteIfExists(archivo);
        }
    }

    @Test
    public void testCargarArchivoConLineasMalasOInexistente() throws IOException {
        Path inexistente = Path.of("archivo-que-no-existe-12345.txt");
        assertTrue(TablaPuntajes.cargar(inexistente).getMejores().isEmpty());

        Path archivo = Files.createTempFile("puntajes-danados", ".txt");
        try {
            Files.write(archivo, List.of("Sofia;3200;4", "", "linea mala", "Carlos;1450;2"), StandardCharsets.UTF_8);
            TablaPuntajes cargada = TablaPuntajes.cargar(archivo);
            assertEquals(2, cargada.getMejores().size());
        } finally {
            Files.deleteIfExists(archivo);
        }
    }

    @Test
    public void testCapacidadInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new TablaPuntajes(0));
        assertEquals(TablaPuntajes.MAXIMO_POR_DEFECTO, new TablaPuntajes().getMaximo());
    }
}
