package minero.modelo;

/**
 * Pregunta de operaciones combinadas con la forma a + b x c = ?,
 * respetando la jerarquia de operaciones.
 */
public class PreguntaCombinada extends Pregunta {

    /**
     * Primer operando (sumando).
     */
    private int a;

    /**
     * Segundo operando (primer factor).
     */
    private int b;

    /**
     * Tercer operando (segundo factor).
     */
    private int c;

    /**
     * Crea una pregunta de operacion combinada.
     *
     * @param a sumando
     * @param b primer factor
     * @param c segundo factor
     * @throws IllegalArgumentException si algun operando es negativo
     */
    public PreguntaCombinada(int a, int b, int c) {
        super();
        if (a < 0 || b < 0 || c < 0) {
            throw new IllegalArgumentException("Los operandos no pueden ser negativos");
        }
        this.a = a;
        this.b = b;
        this.c = c;
        generarOpciones();
    }

    /**
     * Calcula a + (b * c) respetando el orden de operaciones.
     *
     * @return resultado de la operacion combinada
     */
    @Override
    public int calcularResultado() {
        return a + (b * c);
    }

    /**
     * Devuelve el enunciado de la operacion combinada.
     *
     * @return texto con el formato "a + b x c = ?"
     */
    @Override
    public String getEnunciado() {
        return a + " + " + b + " x " + c + " = ?";
    }
}
