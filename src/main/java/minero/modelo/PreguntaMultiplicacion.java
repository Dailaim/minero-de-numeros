package minero.modelo;

/**
 * Pregunta de multiplicacion entre dos numeros enteros no negativos.
 */
public class PreguntaMultiplicacion extends Pregunta {

    /**
     * Primer factor.
     */
    private int a;

    /**
     * Segundo factor.
     */
    private int b;

    /**
     * Crea una pregunta de multiplicacion.
     *
     * @param a primer factor
     * @param b segundo factor
     * @throws IllegalArgumentException si alguno de los factores es negativo
     */
    public PreguntaMultiplicacion(int a, int b) {
        super();
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Los factores no pueden ser negativos");
        }
        this.a = a;
        this.b = b;
        generarOpciones();
    }

    /**
     * Calcula el producto de los dos factores.
     *
     * @return resultado de a * b
     */
    @Override
    public int calcularResultado() {
        return a * b;
    }

    /**
     * Devuelve el enunciado de la multiplicacion.
     *
     * @return texto con el formato "a x b = ?"
     */
    @Override
    public String getEnunciado() {
        return a + " x " + b + " = ?";
    }
}
