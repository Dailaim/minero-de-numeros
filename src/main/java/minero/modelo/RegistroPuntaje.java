package minero.modelo;

import java.util.Objects;

/**
 * Almacena el resultado final de una partida para mostrarlo en la tabla de
 * mejores puntajes.
 */
public class RegistroPuntaje implements Comparable<RegistroPuntaje> {

    /**
     * Caracter separador usado al guardar el registro en texto.
     */
    static final String SEPARADOR = ";";

    /**
     * Nombre del jugador.
     */
    private String nombre;

    /**
     * Puntaje total alcanzado.
     */
    private int puntaje;

    /**
     * Cantidad de gemas obtenidas (0 a 4).
     */
    private int gemas;

    /**
     * Crea un nuevo registro de puntaje.
     *
     * @param nombre nombre del jugador
     * @param puntaje puntaje obtenido (no negativo)
     * @param gemas cantidad de gemas ganadas (no negativa)
     * @throws IllegalArgumentException si alguno de los datos es invalido
     */
    public RegistroPuntaje(String nombre, int puntaje, int gemas) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (puntaje < 0 || gemas < 0) {
            throw new IllegalArgumentException("El puntaje y las gemas no pueden ser negativos");
        }
        this.nombre = nombre.trim().replace(SEPARADOR, ",");
        this.puntaje = puntaje;
        this.gemas = gemas;
    }

    /**
     * Convierte el registro a una linea de texto separada por punto y coma.
     *
     * @return linea de texto con formato nombre;puntaje;gemas
     */
    public String aLinea() {
        return nombre + SEPARADOR + puntaje + SEPARADOR + gemas;
    }

    /**
     * Crea un registro leyendo una linea de texto con formato nombre;puntaje;gemas.
     *
     * @param linea linea leida del archivo
     * @return registro reconstruido
     * @throws IllegalArgumentException si la linea no tiene el formato correcto
     */
    public static RegistroPuntaje desdeLinea(String linea) {
        if (linea == null) {
            throw new IllegalArgumentException("La linea no puede ser nula");
        }
        String[] partes = linea.split(SEPARADOR);
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato de linea invalido: " + linea);
        }
        try {
            String nombre = partes[0];
            int puntaje = Integer.parseInt(partes[1].trim());
            int gemas = Integer.parseInt(partes[2].trim());
            return new RegistroPuntaje(nombre, puntaje, gemas);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error al leer numeros en la linea: " + linea, e);
        }
    }

    /**
     * Compara dos registros ordenando de mayor a menor puntaje, y usando las
     * gemas como desempate.
     *
     * @param otro registro a comparar
     * @return valor negativo si este registro va primero
     */
    @Override
    public int compareTo(RegistroPuntaje otro) {
        if (this.puntaje != otro.puntaje) {
            return Integer.compare(otro.puntaje, this.puntaje);
        }
        return Integer.compare(otro.gemas, this.gemas);
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el puntaje guardado.
     *
     * @return puntaje
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Obtiene la cantidad de gemas ganadas.
     *
     * @return numero de gemas
     */
    public int getGemas() {
        return gemas;
    }

    /**
     * Compara si dos registros tienen el mismo nombre, puntaje y gemas.
     *
     * @param obj objeto a comparar
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RegistroPuntaje)) {
            return false;
        }
        RegistroPuntaje otro = (RegistroPuntaje) obj;
        return this.puntaje == otro.puntaje
                && this.gemas == otro.gemas
                && this.nombre.equals(otro.nombre);
    }

    /**
     * Genera el codigo hash del registro.
     *
     * @return codigo hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre, puntaje, gemas);
    }

    /**
     * Devuelve el registro en un formato legible para mostrar en pantalla.
     *
     * @return texto con nombre, puntaje y gemas
     */
    @Override
    public String toString() {
        return nombre + " - " + puntaje + " pts (" + gemas + " gemas)";
    }
}
