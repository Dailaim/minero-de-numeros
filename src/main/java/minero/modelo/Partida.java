package minero.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Controla el desarrollo de una partida de Minero de Numeros, coordinando al
 * jugador, los niveles, los puntajes y las condiciones de victoria o derrota.
 */
public class Partida {

    /**
     * Tiempo limite en segundos para responder cada pregunta.
     */
    public static final int SEGUNDOS_POR_PREGUNTA = 20;

    /**
     * Numero de preguntas configuradas para cada uno de los 4 niveles.
     */
    private static final int[] PREGUNTAS_POR_NIVEL = {6, 5, 4, 3};

    /**
     * Recompensa correspondiente a cada nivel.
     */
    private static final Gema[] GEMAS_POR_NIVEL = {Gema.ORO, Gema.ESMERALDA, Gema.RUBI, Gema.DIAMANTE};

    /**
     * Jugador de la partida actual.
     */
    private Jugador jugador;

    /**
     * Lista de niveles de la mina.
     */
    private List<Nivel> niveles;

    /**
     * Calculador usado para asignar puntos y bonos.
     */
    private CalculadorPuntaje calculador;

    /**
     * Posicion del nivel que se esta jugando.
     */
    private int indiceNivel;

    /**
     * Estado actual de la partida (en curso, victoria o derrota).
     */
    private EstadoPartida estado;

    /**
     * Crea una partida con un jugador, una lista de niveles y un calculador de puntaje.
     *
     * @param jugador jugador de la partida
     * @param niveles niveles a jugar en orden
     * @param calculador calculador de puntajes
     * @throws IllegalArgumentException si algun argumento es nulo o no hay niveles
     */
    public Partida(Jugador jugador, List<Nivel> niveles, CalculadorPuntaje calculador) {
        if (jugador == null || niveles == null || calculador == null) {
            throw new IllegalArgumentException("Los componentes de la partida no pueden ser nulos");
        }
        if (niveles.isEmpty()) {
            throw new IllegalArgumentException("La partida debe tener al menos un nivel");
        }
        this.jugador = jugador;
        this.niveles = new ArrayList<>(niveles);
        this.calculador = calculador;
        this.indiceNivel = 0;
        this.estado = EstadoPartida.EN_CURSO;
    }

    /**
     * Crea una partida estandar con los 4 niveles definidos en el juego.
     *
     * @param nombreJugador nombre del jugador
     * @return partida lista para iniciar
     */
    public static Partida nueva(String nombreJugador) {
        List<Nivel> listaNiveles = new ArrayList<>();
        for (int i = 0; i < PREGUNTAS_POR_NIVEL.length; i++) {
            int numeroNivel = i + 1;
            GeneradorPorNivel generador = new GeneradorPorNivel(numeroNivel);
            listaNiveles.add(new Nivel(numeroNivel, PREGUNTAS_POR_NIVEL[i], GEMAS_POR_NIVEL[i], generador));
        }
        return new Partida(new Jugador(nombreJugador), listaNiveles, new CalculadorPuntaje());
    }

    /**
     * Procesa la respuesta del jugador en la pregunta actual.
     *
     * @param respuesta opcion seleccionada
     * @param segundosRestantes segundos sobrantes en el temporizador (0 a 20)
     * @return true si la respuesta fue correcta, false si hubo derrumbe
     * @throws IllegalStateException si la partida ya termino
     * @throws IllegalArgumentException si los segundos estan fuera de rango
     */
    public boolean responder(int respuesta, int segundosRestantes) {
        verificarEnCurso();
        int puntosGanados = calculador.puntosPorRespuesta(segundosRestantes);
        Nivel nivelActual = getNivelActual();

        if (nivelActual.responder(respuesta)) {
            jugador.sumarPuntos(puntosGanados);
            if (nivelActual.estaCompletado()) {
                completarNivel(nivelActual);
            }
            return true;
        } else {
            registrarDerrumbe();
            return false;
        }
    }

    /**
     * Aplica la penalizacion cuando se acaba el tiempo de una pregunta:
     * se pierde una vida y se reemplaza la pregunta.
     *
     * @throws IllegalStateException si la partida ya termino
     */
    public void agotarTiempo() {
        verificarEnCurso();
        getNivelActual().registrarFallo();
        registrarDerrumbe();
    }

    /**
     * Genera un registro de puntaje al finalizar la partida para guardarlo en el ranking.
     *
     * @return registro con nombre, puntos y cantidad de gemas
     * @throws IllegalStateException si la partida aun no ha terminado
     */
    public RegistroPuntaje crearRegistro() {
        if (!estaTerminada()) {
            throw new IllegalStateException("La partida todavia no ha terminado");
        }
        return new RegistroPuntaje(jugador.getNombre(), jugador.getPuntos(), jugador.getGemas().size());
    }

    /**
     * Devuelve el nivel que se esta jugando actualmente.
     *
     * @return nivel actual
     * @throws IllegalStateException si ya se completaron todos los niveles
     */
    public Nivel getNivelActual() {
        if (indiceNivel >= niveles.size()) {
            throw new IllegalStateException("Ya se completaron todos los niveles");
        }
        return niveles.get(indiceNivel);
    }

    /**
     * Devuelve la pregunta activa del nivel actual.
     *
     * @return pregunta activa
     */
    public Pregunta getPreguntaActual() {
        return getNivelActual().getPreguntaActual();
    }

    /**
     * Indica si la partida ya termino por victoria o por derrota.
     *
     * @return true si la partida ya no esta en curso
     */
    public boolean estaTerminada() {
        return estado != EstadoPartida.EN_CURSO;
    }

    /**
     * Devuelve el estado actual de la partida.
     *
     * @return estado de la partida
     */
    public EstadoPartida getEstado() {
        return estado;
    }

    /**
     * Devuelve el jugador de la partida.
     *
     * @return jugador
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Devuelve una copia de la lista de niveles de la partida.
     *
     * @return lista de niveles
     */
    public List<Nivel> getNiveles() {
        return new ArrayList<>(niveles);
    }

    /**
     * Devuelve la cantidad total de niveles de la partida.
     *
     * @return numero de niveles
     */
    public int getCantidadNiveles() {
        return niveles.size();
    }

    /**
     * Suma el bono del nivel, entrega la gema y avanza al siguiente nivel o
     * finaliza la partida en victoria.
     *
     * @param nivel nivel completado
     */
    private void completarNivel(Nivel nivel) {
        jugador.sumarPuntos(calculador.bonoPorNivel(nivel.getNumero()));
        jugador.agregarGema(nivel.getRecompensa());
        indiceNivel++;
        if (indiceNivel >= niveles.size()) {
            estado = EstadoPartida.VICTORIA;
            jugador.sumarPuntos(calculador.bonoPorVidas(jugador.getVidas()));
        }
    }

    /**
     * Resta una vida al jugador y cambia el estado a derrota si llega a cero.
     */
    private void registrarDerrumbe() {
        jugador.perderVida();
        if (!jugador.tieneVidas()) {
            estado = EstadoPartida.DERROTA;
        }
    }

    /**
     * Valida que la partida siga activa antes de realizar una accion.
     *
     * @throws IllegalStateException si la partida ya termino
     */
    private void verificarEnCurso() {
        if (estaTerminada()) {
            throw new IllegalStateException("La partida ya termino: " + estado);
        }
    }
}
