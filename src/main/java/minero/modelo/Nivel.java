package minero.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un nivel de la mina con su lista de preguntas, el progreso del
 * jugador y la gema que se obtiene al completarlo.
 */
public class Nivel {

    /**
     * Numero identificador del nivel (1 a 4).
     */
    private int numero;

    /**
     * Gema que se entrega al completar todas las preguntas.
     */
    private Gema recompensa;

    /**
     * Generador usado para crear las preguntas iniciales y los reemplazos.
     */
    private GeneradorPreguntas generador;

    /**
     * Lista de preguntas del nivel.
     */
    private List<Pregunta> preguntas;

    /**
     * Indice de la pregunta que se encuentra activa actualmente.
     */
    private int indiceActual;

    /**
     * Crea un nivel y genera su lista inicial de preguntas.
     *
     * @param numero numero del nivel (mayor a 0)
     * @param cantidadPreguntas numero de preguntas del nivel
     * @param recompensa gema de recompensa
     * @param generador generador de preguntas para el nivel
     * @throws IllegalArgumentException si algun parametro es invalido o nulo
     */
    public Nivel(int numero, int cantidadPreguntas, Gema recompensa, GeneradorPreguntas generador) {
        if (numero < 1) {
            throw new IllegalArgumentException("El numero de nivel debe ser mayor a 0");
        }
        if (cantidadPreguntas < 1) {
            throw new IllegalArgumentException("El nivel debe tener al menos una pregunta");
        }
        if (recompensa == null) {
            throw new IllegalArgumentException("La recompensa no puede ser nula");
        }
        if (generador == null) {
            throw new IllegalArgumentException("El generador no puede ser nulo");
        }

        this.numero = numero;
        this.recompensa = recompensa;
        this.generador = generador;
        this.preguntas = new ArrayList<>();
        for (int i = 0; i < cantidadPreguntas; i++) {
            preguntas.add(generador.generar());
        }
        this.indiceActual = 0;
    }

    /**
     * Evalua la respuesta del jugador para la pregunta actual. Si acierta avanza
     * a la siguiente; si falla, la pregunta actual se reemplaza por una nueva.
     *
     * @param respuesta valor elegido por el jugador
     * @return true si la respuesta es correcta, false si fallo
     * @throws IllegalStateException si el nivel ya fue completado
     */
    public boolean responder(int respuesta) {
        Pregunta actual = getPreguntaActual();
        if (actual.esCorrecta(respuesta)) {
            indiceActual++;
            return true;
        } else {
            reemplazarPreguntaActual();
            return false;
        }
    }

    /**
     * Registra un error por tiempo agotado reemplazando la pregunta activa.
     *
     * @throws IllegalStateException si el nivel ya fue completado
     */
    public void registrarFallo() {
        getPreguntaActual();
        reemplazarPreguntaActual();
    }

    /**
     * Devuelve la pregunta que esta activa en este momento.
     *
     * @return pregunta actual
     * @throws IllegalStateException si ya no quedan preguntas por responder
     */
    public Pregunta getPreguntaActual() {
        if (estaCompletado()) {
            throw new IllegalStateException("El nivel " + numero + " ya esta completado");
        }
        return preguntas.get(indiceActual);
    }

    /**
     * Obtiene la pregunta ubicada en la posicion indicada.
     *
     * @param indice posicion de la pregunta (desde 0)
     * @return pregunta en esa posicion
     */
    public Pregunta getPregunta(int indice) {
        return preguntas.get(indice);
    }

    /**
     * Indica si la pregunta en esa posicion ya esta desbloqueada (resuelta o activa).
     *
     * @param indice posicion de la pregunta
     * @return true si esta desbloqueada
     */
    public boolean estaDesbloqueada(int indice) {
        return indice >= 0 && indice <= indiceActual && indice < preguntas.size();
    }

    /**
     * Indica si la pregunta en esa posicion ya fue resuelta.
     *
     * @param indice posicion de la pregunta
     * @return true si ya fue respondida correctamente
     */
    public boolean estaResuelta(int indice) {
        return indice >= 0 && indice < indiceActual;
    }

    /**
     * Indica si el jugador ya respondio todas las preguntas del nivel.
     *
     * @return true si el nivel esta terminado
     */
    public boolean estaCompletado() {
        return indiceActual >= preguntas.size();
    }

    /**
     * Devuelve el numero total de preguntas del nivel.
     *
     * @return cantidad de preguntas
     */
    public int getCantidadPreguntas() {
        return preguntas.size();
    }

    /**
     * Devuelve cuantas preguntas se han resuelto hasta el momento.
     *
     * @return preguntas resueltas
     */
    public int getPreguntasResueltas() {
        return indiceActual;
    }

    /**
     * Devuelve el numero del nivel.
     *
     * @return numero del nivel
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Devuelve la gema de recompensa del nivel.
     *
     * @return gema del nivel
     */
    public Gema getRecompensa() {
        return recompensa;
    }

    /**
     * Reemplaza la pregunta actual por otra generada aleatoriamente.
     */
    private void reemplazarPreguntaActual() {
        preguntas.set(indiceActual, generador.generar());
    }
}
