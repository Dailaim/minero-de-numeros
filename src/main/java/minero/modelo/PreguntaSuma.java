package minero.modelo;

/**
 * Pregunta de suma entre dos numeros enteros no negativos.
 */
public class PreguntaSuma extends Pregunta {

    /**
     * Primer sumando.
     */
    private int a;

    /**
     * Segundo sumando.
     */
    private int b;

    /**
     * Crea una pregunta de suma con los dos numeros indicados.
     *
     * @param a primer numero
     * @param b segundo numero
     * @throws IllegalArgumentException si alguno de los numeros es negativo
     */
    public PreguntaSuma(int a, int b) {
        super();
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Los sumandos no pueden ser negativos");
        }
        this.a = a;
        this.b = b;
        generarOpciones();
    }

    /**
     * Calcula la suma de los dos operandos.
     *
     * @return resultado de a + b
     */
    @Override
    public int calcularResultado() {
        return a + b;
    }

    /**
     * Devuelve el enunciado de la suma.
     *
     * @return texto con el formato "a + b = ?"
     */
    @Override
    public String getEnunciado() {
        return a + " + " + b + " = ?";
    }
}
