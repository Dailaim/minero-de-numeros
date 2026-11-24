package minero.modelo;

/**
 * Interfaz para las clases encargadas de crear preguntas para un nivel.
 */
public interface GeneradorPreguntas {

    /**
     * Genera una nueva pregunta.
     *
     * @return pregunta generada
     */
    Pregunta generar();
}
