package minero.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Clase abstracta base para las preguntas matematicas del juego.
 * Maneja las 4 opciones de respuesta y la validacion de la respuesta correcta.
 */
public abstract class Pregunta {

    /**
     * Cantidad de opciones de respuesta por pregunta.
     */
    public static final int NUMERO_OPCIONES = 4;

    /**
     * Rango maximo de diferencia entre la respuesta correcta y las opciones falsas.
     */
    private static final int RANGO_DISTRACTORES = 10;

    /**
     * Lista con las 4 opciones de respuesta mezcladas.
     */
    protected List<Integer> opciones;

    /**
     * Constructor base de una pregunta.
     */
    protected Pregunta() {
        this.opciones = new ArrayList<>();
    }

    /**
     * Calcula el resultado correcto de la operacion.
     *
     * @return resultado correcto
     */
    public abstract int calcularResultado();

    /**
     * Construye el texto de la pregunta para mostrarlo al usuario.
     *
     * @return enunciado de la operacion
     */
    public abstract String getEnunciado();

    /**
     * Comprueba si la respuesta elegida coincide con el resultado correcto.
     *
     * @param respuesta opcion seleccionada por el jugador
     * @return true si es correcta, false en caso contrario
     */
    public boolean esCorrecta(int respuesta) {
        return respuesta == calcularResultado();
    }

    /**
     * Devuelve las opciones de respuesta de la pregunta.
     *
     * @return lista con las 4 opciones
     */
    public List<Integer> getOpciones() {
        return new ArrayList<>(opciones);
    }

    /**
     * Genera la respuesta correcta junto con tres opciones incorrectas cercanas
     * y las mezcla en orden aleatorio.
     */
    protected void generarOpciones() {
        Random random = new Random();
        int correcta = calcularResultado();
        opciones.clear();
        opciones.add(correcta);

        while (opciones.size() < NUMERO_OPCIONES) {
            int diferencia = random.nextInt(RANGO_DISTRACTORES) + 1;
            int opcion;
            if (random.nextBoolean()) {
                opcion = correcta + diferencia;
            } else {
                opcion = correcta - diferencia;
            }

            if (opcion >= 0 && !opciones.contains(opcion)) {
                opciones.add(opcion);
            }
        }

        Collections.shuffle(opciones, random);
    }

    /**
     * Devuelve el enunciado como representacion en texto de la pregunta.
     *
     * @return enunciado de la pregunta
     */
    @Override
    public String toString() {
        return getEnunciado();
    }
}
