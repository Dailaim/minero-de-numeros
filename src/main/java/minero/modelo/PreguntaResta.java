package minero.modelo;

/**
 * Pregunta de resta entre dos numeros con resultado mayor o igual a cero.
 */
public class PreguntaResta extends Pregunta {

    /**
     * Numero del cual se resta.
     */
    private int minuendo;

    /**
     * Numero que se resta.
     */
    private int sustraendo;

    /**
     * Crea una pregunta de resta.
     *
     * @param minuendo numero mayor o igual al sustraendo
     * @param sustraendo numero a restar
     * @throws IllegalArgumentException si el sustraendo es negativo o el resultado daria negativo
     */
    public PreguntaResta(int minuendo, int sustraendo) {
        super();
        if (sustraendo < 0 || minuendo < sustraendo) {
            throw new IllegalArgumentException("La resta no puede dar un resultado negativo");
        }
        this.minuendo = minuendo;
        this.sustraendo = sustraendo;
        generarOpciones();
    }

    /**
     * Calcula la diferencia entre minuendo y sustraendo.
     *
     * @return resultado de minuendo - sustraendo
     */
    @Override
    public int calcularResultado() {
        return minuendo - sustraendo;
    }

    /**
     * Devuelve el enunciado de la resta.
     *
     * @return texto con el formato "a - b = ?"
     */
    @Override
    public String getEnunciado() {
        return minuendo + " - " + sustraendo + " = ?";
    }
}
