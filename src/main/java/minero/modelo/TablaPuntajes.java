package minero.modelo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Administra la lista de los mejores puntajes del juego y permite guardarlos y
 * cargarlos desde un archivo de texto.
 */
public class TablaPuntajes {

    /**
     * Capacidad por defecto de la tabla de puntajes.
     */
    public static final int MAXIMO_POR_DEFECTO = 10;

    /**
     * Numero maximo de registros que guarda la tabla.
     */
    private int maximo;

    /**
     * Lista de registros ordenados de mayor a menor.
     */
    private List<RegistroPuntaje> registros;

    /**
     * Crea una tabla vacia con la capacidad por defecto (10 registros).
     */
    public TablaPuntajes() {
        this(MAXIMO_POR_DEFECTO);
    }

    /**
     * Crea una tabla vacia con el limite de registros indicado.
     *
     * @param maximo cantidad maxima de puntajes a guardar
     * @throws IllegalArgumentException si el maximo es menor a 1
     */
    public TablaPuntajes(int maximo) {
        if (maximo < 1) {
            throw new IllegalArgumentException("El maximo debe ser mayor a 0");
        }
        this.maximo = maximo;
        this.registros = new ArrayList<>();
    }

    /**
     * Intenta agregar un nuevo registro manteniendo el orden descendente.
     * Si se supera el limite, se elimina el puntaje mas bajo.
     *
     * @param registro puntaje a insertar
     * @return true si el registro quedo guardado en la tabla
     * @throws IllegalArgumentException si el registro es nulo
     */
    public boolean agregar(RegistroPuntaje registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        registros.add(registro);
        Collections.sort(registros);
        if (registros.size() > maximo) {
            RegistroPuntaje eliminado = registros.remove(registros.size() - 1);
            return eliminado != registro;
        }
        return true;
    }

    /**
     * Verifica si un puntaje alcanza para entrar en la tabla.
     *
     * @param puntaje puntaje a revisar
     * @return true si hay cupo o supera al ultimo puntaje guardado
     */
    public boolean esMejorPuntaje(int puntaje) {
        if (registros.size() < maximo) {
            return true;
        }
        RegistroPuntaje ultimo = registros.get(registros.size() - 1);
        return puntaje > ultimo.getPuntaje();
    }

    /**
     * Devuelve una copia de los mejores puntajes ordenados.
     *
     * @return lista de registros
     */
    public List<RegistroPuntaje> getMejores() {
        return new ArrayList<>(registros);
    }

    /**
     * Devuelve el limite maximo de registros de la tabla.
     *
     * @return capacidad maxima
     */
    public int getMaximo() {
        return maximo;
    }

    /**
     * Guarda los registros actuales en un archivo de texto.
     *
     * @param archivo ruta del archivo de destino
     * @throws IOException si ocurre un error al escribir
     */
    public void guardar(Path archivo) throws IOException {
        List<String> lineas = new ArrayList<>();
        for (RegistroPuntaje registro : registros) {
            lineas.add(registro.aLinea());
        }
        Files.write(archivo, lineas, StandardCharsets.UTF_8);
    }

    /**
     * Carga una tabla de puntajes desde un archivo de texto. Si el archivo no
     * existe devuelve una tabla vacia.
     *
     * @param archivo ruta del archivo a leer
     * @return tabla con los puntajes encontrados
     * @throws IOException si falla la lectura del archivo
     */
    public static TablaPuntajes cargar(Path archivo) throws IOException {
        TablaPuntajes tabla = new TablaPuntajes();
        if (!Files.exists(archivo)) {
            return tabla;
        }
        List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
        for (String linea : lineas) {
            if (linea.trim().isEmpty()) {
                continue;
            }
            try {
                tabla.agregar(RegistroPuntaje.desdeLinea(linea));
            } catch (IllegalArgumentException e) {
                // Si alguna linea esta mal formada se omite
            }
        }
        return tabla;
    }
}
