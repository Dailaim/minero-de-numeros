package minero.modelo;

/**
 * Representa los estados en los que puede estar una partida.
 */
public enum EstadoPartida {

    /**
     * La partida sigue activa.
     */
    EN_CURSO,

    /**
     * El jugador completo todos los niveles.
     */
    VICTORIA,

    /**
     * El jugador perdio todas sus vidas.
     */
    DERROTA
}
