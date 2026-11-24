package minero.modelo;

import java.util.Random;

/**
 * Genera preguntas aleatorias de acuerdo con la dificultad de cada uno de los
 * cuatro niveles de la mina.
 */
public class GeneradorPorNivel implements GeneradorPreguntas {

    /**
     * Numero maximo de nivel permitido.
     */
    public static final int NIVEL_MAXIMO = 4;

    /**
     * Nivel de dificultad configurado (1 a 4).
     */
    private int nivel;

    /**
     * Generador de numeros aleatorios.
     */
    private Random random;

    /**
     * Crea un generador para el nivel indicado.
     *
     * @param nivel numero del nivel entre 1 y 4
     * @throws IllegalArgumentException si el nivel esta fuera de rango
     */
    public GeneradorPorNivel(int nivel) {
        if (nivel < 1 || nivel > NIVEL_MAXIMO) {
            throw new IllegalArgumentException("Nivel fuera de rango: " + nivel);
        }
        this.nivel = nivel;
        this.random = new Random();
    }

    /**
     * Genera una pregunta aleatoria acorde al nivel actual.
     *
     * @return nueva pregunta
     */
    @Override
    public Pregunta generar() {
        switch (nivel) {
            case 1:
                return generarNivel1();
            case 2:
                return generarNivel2();
            case 3:
                return generarNivel3();
            default:
                return generarNivel4();
        }
    }

    /**
     * Devuelve el nivel asignado al generador.
     *
     * @return numero del nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Genera sumas y restas con numeros del 1 al 20.
     *
     * @return pregunta de nivel 1
     */
    private Pregunta generarNivel1() {
        int a = numeroEntre(1, 20);
        int b = numeroEntre(1, 20);
        if (random.nextBoolean()) {
            return new PreguntaSuma(a, b);
        } else {
            return new PreguntaResta(Math.max(a, b), Math.min(a, b));
        }
    }

    /**
     * Genera sumas y restas hasta 100 o tablas de multiplicar hasta el 10.
     *
     * @return pregunta de nivel 2
     */
    private Pregunta generarNivel2() {
        int tipo = random.nextInt(3);
        if (tipo == 0) {
            int a = numeroEntre(1, 99);
            int b = numeroEntre(1, 100 - a);
            return new PreguntaSuma(a, b);
        } else if (tipo == 1) {
            int minuendo = numeroEntre(1, 100);
            int sustraendo = numeroEntre(1, minuendo);
            return new PreguntaResta(minuendo, sustraendo);
        } else {
            return new PreguntaMultiplicacion(numeroEntre(1, 10), numeroEntre(1, 10));
        }
    }

    /**
     * Genera multiplicaciones o divisiones exactas con numeros del 2 al 12.
     *
     * @return pregunta de nivel 3
     */
    private Pregunta generarNivel3() {
        int a = numeroEntre(2, 12);
        int b = numeroEntre(2, 12);
        if (random.nextBoolean()) {
            return new PreguntaMultiplicacion(a, b);
        } else {
            return new PreguntaDivision(a * b, b);
        }
    }

    /**
     * Genera operaciones combinadas del tipo a + b x c.
     *
     * @return pregunta de nivel 4
     */
    private Pregunta generarNivel4() {
        int a = numeroEntre(1, 20);
        int b = numeroEntre(2, 10);
        int c = numeroEntre(2, 10);
        return new PreguntaCombinada(a, b, c);
    }

    /**
     * Retorna un numero aleatorio entre minimo y maximo (incluidos).
     *
     * @param minimo limite inferior
     * @param maximo limite superior
     * @return entero aleatorio dentro del rango
     */
    private int numeroEntre(int minimo, int maximo) {
        return minimo + random.nextInt(maximo - minimo + 1);
    }
}
