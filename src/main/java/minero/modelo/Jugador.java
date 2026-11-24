package minero.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al jugador (minero) dentro de la partida, guardando su nombre,
 * vidas restantes, puntaje acumulado y gemas ganadas.
 */
public class Jugador {

    /**
     * Cantidad de vidas con las que inicia el jugador.
     */
    public static final int VIDAS_INICIALES = 3;

    /**
     * Nombre del jugador.
     */
    private String nombre;

    /**
     * Vidas actuales del jugador.
     */
    private int vidas;

    /**
     * Puntos acumulados durante la partida.
     */
    private int puntos;

    /**
     * Lista de gemas obtenidas al superar niveles.
     */
    private List<Gema> gemas;

    /**
     * Crea un jugador nuevo con 3 vidas, 0 puntos y sin gemas.
     *
     * @param nombre nombre del jugador
     * @throws IllegalArgumentException si el nombre es nulo o esta vacio
     */
    public Jugador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacio");
        }
        this.nombre = nombre.trim();
        this.vidas = VIDAS_INICIALES;
        this.puntos = 0;
        this.gemas = new ArrayList<>();
    }

    /**
     * Resta una vida al jugador cuando ocurre un derrumbe.
     */
    public void perderVida() {
        if (vidas > 0) {
            vidas--;
        }
    }

    /**
     * Suma una cantidad de puntos al puntaje total del jugador.
     *
     * @param cantidad puntos a sumar
     * @throws IllegalArgumentException si la cantidad es negativa
     */
    public void sumarPuntos(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("No se pueden sumar puntos negativos");
        }
        this.puntos += cantidad;
    }

    /**
     * Agrega una gema a la lista de recompensas del jugador.
     *
     * @param gema gema obtenida
     * @throws IllegalArgumentException si la gema es nula
     */
    public void agregarGema(Gema gema) {
        if (gema == null) {
            throw new IllegalArgumentException("La gema no puede ser nula");
        }
        gemas.add(gema);
    }

    /**
     * Verifica si el jugador aun tiene vidas disponibles.
     *
     * @return true si tiene al menos una vida
     */
    public boolean tieneVidas() {
        return vidas > 0;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene las vidas restantes.
     *
     * @return cantidad de vidas
     */
    public int getVidas() {
        return vidas;
    }

    /**
     * Obtiene los puntos acumulados.
     *
     * @return puntaje actual
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Devuelve una copia de la lista de gemas obtenidas.
     *
     * @return lista de gemas
     */
    public List<Gema> getGemas() {
        return new ArrayList<>(gemas);
    }
}
