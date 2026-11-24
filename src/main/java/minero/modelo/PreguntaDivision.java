package minero.modelo;

/**
 * Pregunta de division exacta entre dos numeros enteros.
 */
public class PreguntaDivision extends Pregunta {

    /**
     * Numero que se divide.
     */
    private int dividendo;

    /**
     * Numero por el cual se divide.
     */
    private int divisor;

    /**
     * Crea una pregunta de division exacta.
     *
     * @param dividendo numero a dividir (mayor o igual a 0)
     * @param divisor divisor positivo que divida exactamente al dividendo
     * @throws IllegalArgumentException si los valores no son validos o la division no es exacta
     */
    public PreguntaDivision(int dividendo, int divisor) {
        super();
        if (divisor <= 0 || dividendo < 0) {
            throw new IllegalArgumentException("El divisor debe ser positivo y el dividendo no negativo");
        }
        if (dividendo % divisor != 0) {
            throw new IllegalArgumentException("La division debe ser exacta");
        }
        this.dividendo = dividendo;
        this.divisor = divisor;
        generarOpciones();
    }

    /**
     * Calcula el cociente exacto de la division.
     *
     * @return resultado de dividendo / divisor
     */
    @Override
    public int calcularResultado() {
        return dividendo / divisor;
    }

    /**
     * Devuelve el enunciado de la division.
     *
     * @return texto con el formato "a / b = ?"
     */
    @Override
    public String getEnunciado() {
        return dividendo + " / " + divisor + " = ?";
    }
}
