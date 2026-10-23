

package com.mycompany.vogel;
import java.util.Scanner;

public class Vogel {

    private static Scanner op = new Scanner(System.in);

    private int filas;
    private int columnas;
    private int[][] matriz;

    private int[] oferta;
    private int[] demanda;

    public Vogel(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
    }

    public static void main(String[] args) {

        System.out.println("Ingresa el numero de filas:");
        int filas = op.nextInt();

        System.out.println("Ingresa el numero de columnas:");
        int columnas = op.nextInt();

        if (filas <= 0 || columnas <= 0) {
            System.out.println("Las filas y columnas deben ser mayores a cero.");
            return;
        }

        Vogel v = new Vogel(filas, columnas);

        v.llenarMatriz();
        v.mostrarMatriz();
        v.resolver();
    }

    // Llenar matriz, oferta y demanda
    public void llenarMatriz() {

        matriz = new int[filas][columnas];

        System.out.println("Ingresa los costos de la matriz:");

        for (int i = 0; i < filas; i++) {
            System.out.println("Fila " + (i + 1) + ":");

            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = op.nextInt();
            }
        }

        oferta = new int[filas];
        demanda = new int[columnas];

        System.out.println("Ingresa la oferta de cada fila:");

        for (int i = 0; i < filas; i++) {
            do {
                System.out.println("Oferta de la fila " + (i + 1) + ":");
                oferta[i] = op.nextInt();

                if (oferta[i] < 0) {
                    System.out.println("La oferta no puede ser negativa.");
                }
            } while (oferta[i] < 0);
        }

        System.out.println("Ingresa la demanda de cada columna:");

        for (int j = 0; j < columnas; j++) {
            do {
                System.out.println("Demanda de la columna " + (j + 1) + ":");
                demanda[j] = op.nextInt();

                if (demanda[j] < 0) {
                    System.out.println("La demanda no puede ser negativa.");
                }
            } while (demanda[j] < 0);
        }

        equilibrar();
    }

    // Igualar la oferta total con la demanda total
    private void equilibrar() {

        int totalOferta = 0;
        int totalDemanda = 0;

        for (int i = 0; i < filas; i++) {
            totalOferta += oferta[i];
        }

        for (int j = 0; j < columnas; j++) {
            totalDemanda += demanda[j];
        }

        if (totalOferta == totalDemanda) {
            System.out.println("La matriz esta equilibrada.");
        } else if (totalOferta > totalDemanda) {

            int diferencia = totalOferta - totalDemanda;

            int[][] nuevaMatriz = new int[filas][columnas + 1];

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    nuevaMatriz[i][j] = matriz[i][j];
                }
            }

            matriz = nuevaMatriz;

            int[] nuevaDemanda = new int[columnas + 1];

            for (int j = 0; j < columnas; j++) {
                nuevaDemanda[j] = demanda[j];
            }

            nuevaDemanda[columnas] = diferencia;
            demanda = nuevaDemanda;
            columnas++;

            System.out.println("Se agrego una columna ficticia.");
        } else {

            int diferencia = totalDemanda - totalOferta;

            int[][] nuevaMatriz = new int[filas + 1][columnas];

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    nuevaMatriz[i][j] = matriz[i][j];
                }
            }

            matriz = nuevaMatriz;

            int[] nuevaOferta = new int[filas + 1];

            for (int i = 0; i < filas; i++) {
                nuevaOferta[i] = oferta[i];
            }

            nuevaOferta[filas] = diferencia;
            oferta = nuevaOferta;
            filas++;

            System.out.println("Se agrego una fila ficticia.");
        }
    }

    // Mostrar la matriz con oferta y demanda
    public void mostrarMatriz() {

        System.out.println("\nMatriz de costos:");

        System.out.print("Origen/Destino\t");

        for (int j = 0; j < columnas; j++) {
            System.out.print("C" + (j + 1) + "\t");
        }

        System.out.println("Oferta");

        for (int i = 0; i < filas; i++) {

            System.out.print("F" + (i + 1) + "\t\t");

            for (int j = 0; j < columnas; j++) {
                System.out.print(matriz[i][j] + "\t");
            }

            System.out.println(oferta[i]);
        }

        System.out.print("Demanda\t\t");

        for (int j = 0; j < columnas; j++) {
            System.out.print(demanda[j] + "\t");
        }

        System.out.println();
    }

    // Resolver el metodo de Vogel
    public void resolver() {

        int[][] asignaciones = new int[filas][columnas];

        // Copiar oferta y demanda para conservar los datos originales
        int[] ofertaRestante = new int[filas];
        int[] demandaRestante = new int[columnas];

        boolean[] filasActivas = new boolean[filas];
        boolean[] columnasActivas = new boolean[columnas];

        int totalOferta = 0;

        for (int i = 0; i < filas; i++) {
            ofertaRestante[i] = oferta[i];
            filasActivas[i] = oferta[i] > 0;
            totalOferta += oferta[i];
        }

        for (int j = 0; j < columnas; j++) {
            demandaRestante[j] = demanda[j];
            columnasActivas[j] = demanda[j] > 0;
        }

        while (totalOferta > 0) {

            int[] penalizacionFilas = new int[filas];
            int[] penalizacionColumnas = new int[columnas];

            // Calcular penalizaciones de las filas
            for (int i = 0; i < filas; i++) {

                if (!filasActivas[i]) {
                    penalizacionFilas[i] = -1;
                    continue;
                }

                int menor = Integer.MAX_VALUE;
                int segundoMenor = Integer.MAX_VALUE;

                for (int j = 0; j < columnas; j++) {

                    if (columnasActivas[j]) {

                        int costo = matriz[i][j];

                        if (costo < menor) {
                            segundoMenor = menor;
                            menor = costo;
                        } else if (costo < segundoMenor) {
                            segundoMenor = costo;
                        }
                    }
                }

                if (segundoMenor == Integer.MAX_VALUE) {
                    penalizacionFilas[i] = menor;
                } else {
                    penalizacionFilas[i] = segundoMenor - menor;
                }
            }

            // Calcular penalizaciones de las columnas
            for (int j = 0; j < columnas; j++) {

                if (!columnasActivas[j]) {
                    penalizacionColumnas[j] = -1;
                    continue;
                }

                int menor = Integer.MAX_VALUE;
                int segundoMenor = Integer.MAX_VALUE;

                for (int i = 0; i < filas; i++) {

                    if (filasActivas[i]) {

                        int costo = matriz[i][j];

                        if (costo < menor) {
                            segundoMenor = menor;
                            menor = costo;
                        } else if (costo < segundoMenor) {
                            segundoMenor = costo;
                        }
                    }
                }

                if (segundoMenor == Integer.MAX_VALUE) {
                    penalizacionColumnas[j] = menor;
                } else {
                    penalizacionColumnas[j] = segundoMenor - menor;
                }
            }

            // Buscar la mayor penalizacion
            int mayorFila = Integer.MIN_VALUE;
            int indiceFila = -1;

            for (int i = 0; i < filas; i++) {

                if (filasActivas[i]
                        && penalizacionFilas[i] > mayorFila) {

                    mayorFila = penalizacionFilas[i];
                    indiceFila = i;
                }
            }

            int mayorColumna = Integer.MIN_VALUE;
            int indiceColumna = -1;

            for (int j = 0; j < columnas; j++) {

                if (columnasActivas[j]
                        && penalizacionColumnas[j] > mayorColumna) {

                    mayorColumna = penalizacionColumnas[j];
                    indiceColumna = j;
                }
            }

            int filaElegida = -1;
            int columnaElegida = -1;

            // Elegir la fila o columna con mayor penalizacion
            if (indiceFila != -1
                    && (indiceColumna == -1 || mayorFila >= mayorColumna)) {

                filaElegida = indiceFila;

                int menor = Integer.MAX_VALUE;

                for (int j = 0; j < columnas; j++) {

                    if (columnasActivas[j]
                            && matriz[filaElegida][j] < menor) {

                        menor = matriz[filaElegida][j];
                        columnaElegida = j;
                    }
                }

                System.out.println("Fila elegida: " + (filaElegida + 1));

            } else if (indiceColumna != -1) {

                columnaElegida = indiceColumna;

                int menor = Integer.MAX_VALUE;

                for (int i = 0; i < filas; i++) {

                    if (filasActivas[i]
                            && matriz[i][columnaElegida] < menor) {

                        menor = matriz[i][columnaElegida];
                        filaElegida = i;
                    }
                }

                System.out.println("Columna elegida: "
                        + (columnaElegida + 1));
            }

            if (filaElegida == -1 || columnaElegida == -1) {
                System.out.println("No se pudo realizar otra asignacion.");
                break;
            }

            // Asignar la cantidad menor entre oferta y demanda
            int cantidad = Math.min(
                    ofertaRestante[filaElegida],
                    demandaRestante[columnaElegida]
            );

            asignaciones[filaElegida][columnaElegida] = cantidad;

            System.out.println(
                    "Asignacion: " + cantidad
                    + " en fila " + (filaElegida + 1)
                    + ", columna " + (columnaElegida + 1)
            );

            ofertaRestante[filaElegida] -= cantidad;
            demandaRestante[columnaElegida] -= cantidad;

            totalOferta -= cantidad;

            if (ofertaRestante[filaElegida] == 0) {
                filasActivas[filaElegida] = false;
            }

            if (demandaRestante[columnaElegida] == 0) {
                columnasActivas[columnaElegida] = false;
            }
        }

        // Mostrar las asignaciones y calcular el costo total
        int costoTotal = 0;

        System.out.println("\nMatriz de asignaciones:");

        for (int i = 0; i < filas; i++) {

            for (int j = 0; j < columnas; j++) {

                System.out.print(asignaciones[i][j] + "\t");

                costoTotal += asignaciones[i][j] * matriz[i][j];
            }

            System.out.println();
        }

        System.out.println("Costo total de transporte: " + costoTotal);
    }
}