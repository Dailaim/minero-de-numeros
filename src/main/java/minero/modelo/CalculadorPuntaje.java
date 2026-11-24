package minero.modelo;

/**
 * Clase encargada de calcular los puntos y bonos durante la partida.
 */
public class CalculadorPuntaje {

    /**
     * Puntos base por responder bien una pregunta.
     */
    public static final int PUNTOS_RESPUESTA = 100;

    /**
     * Puntos adicionales por cada segundo restante en el temporizador.
     */
    public static final int PUNTOS_POR_SEGUNDO = 5;

    /**
     * Valor base del bono por completar un nivel.
     */
    public static final int BONO_NIVEL = 500;

    /**
     * Puntos otorgados por cada vida restante al ganar.
     */
    public static final int PUNTOS_POR_VIDA = 200;

    /**
     * Calcula los puntos obtenidos al responder correctamente una pregunta.
     *
     * @param segundosRestantes segundos que quedaron en el temporizador (0 a 20)
     * @return total de puntos de la respuesta
     * @throws IllegalArgumentException si los segundos estan fuera de rango
     */
    public int puntosPorRespuesta(int segundosRestantes) {
        if (segundosRestantes < 0 || segundosRestantes > Partida.SEGUNDOS_POR_PREGUNTA) {
            throw new IllegalArgumentException("Segundos fuera de rango: " + segundosRestantes);
        }
        return PUNTOS_RESPUESTA + (PUNTOS_POR_SEGUNDO * segundosRestantes);
    }

    /**
     * Calcula el bono al terminar un nivel.
     *
     * @param numeroNivel numero del nivel completado
     * @return puntos de bono del nivel
     * @throws IllegalArgumentException si el nivel es menor a 1
     */
    public int bonoPorNivel(int numeroNivel) {
        if (numeroNivel < 1) {
            throw new IllegalArgumentException("El numero de nivel debe ser mayor a 0");
        }
        return BONO_NIVEL * numeroNivel;
    }

    /**
     * Calcula el bono por las vidas que le quedaron al jugador al finalizar.
     *
     * @param vidasRestantes cantidad de vidas restantes
     * @return puntos de bono por vidas
     * @throws IllegalArgumentException si las vidas son negativas
     */
    public int bonoPorVidas(int vidasRestantes) {
        if (vidasRestantes < 0) {
            throw new IllegalArgumentException("Las vidas no pueden ser negativas");
        }
        return PUNTOS_POR_VIDA * vidasRestantes;
    }
}
