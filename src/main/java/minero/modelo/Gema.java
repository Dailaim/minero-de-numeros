package minero.modelo;

/**
 * Gemas que gana el jugador al completar cada nivel de la mina.
 */
public enum Gema {

    /**
     * Gema del nivel 1.
     */
    ORO("Oro"),

    /**
     * Gema del nivel 2.
     */
    ESMERALDA("Esmeralda"),

    /**
     * Gema del nivel 3.
     */
    RUBI("Rubí"),

    /**
     * Gema del nivel 4.
     */
    DIAMANTE("Diamante");

    /**
     * Nombre visible de la gema.
     */
    private String nombre;

    /**
     * Constructor de la gema.
     *
     * @param nombre nombre para mostrar en pantalla
     */
    Gema(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre de la gema.
     *
     * @return nombre visible de la gema
     */
    public String getNombre() {
        return nombre;
    }
}
