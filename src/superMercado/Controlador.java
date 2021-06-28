/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMercado;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author cmateo
 */
public class Controlador {

    Cola filaEspera = new Cola();

    Caja caja1 = new Caja();
    Caja caja2 = new Caja();
    Caja caja3 = new Caja();
    Caja caja4 = new Caja();

    private boolean estadoCaja4 = false;
    private int clientesAtendidos = 0, 
            tamañoMaximoFila = 0, 
            tamañoMedioFila = 0;

    private long tiempoMaximoEspera = 0, 
            tiempoMaximoEsperaAnterior = 0;
    
    private long tiempoOperandoCaja4 = 0l;
    private int arrayTamañoMedioFila[] = new int[100];
    private int elementosTamañoMedio = 0;

    public Controlador() {

        String op = "";
        while (true) {
            caja4.setDisponibilidad(false);
            op = menu();

            if (op.equals("R")) {
                registrarCliente();
            } else if (op.equals("T")) {
                System.exit(0);
                break;
            }
        }
    }

    public String menu() {
        return JOptionPane.showInputDialog(null, "Disponibilidad\n\n" + "-Caja 1: ( " + caja1.getDisponibilidad()
                + " ) \n-Caja 2: ( " + caja2.getDisponibilidad() + " ) \n-Caja 3: ( " + caja3.getDisponibilidad()
                + " ) \n-Caja 4: ( " + caja4.getDisponibilidad() + " )\n\n"
                + "fila espera: " + filaEspera.getNumeroElementos() + " clientes.\n"
                + "clientes atendidos: " + clientesAtendidos + "\n"
                + "tiempo operando caja 4: ( " + (tiempoOperandoCaja4 / 60000) + " m )                \n"
                + "tamaño maximo de fila: " + tamañoMaximoFila + " personas.\n"
                + "tamaño medio de fila: " + filaMedia() + " personas.\n"
                + "tiempo maximo espera: " + (tiempoMaximoEspera / 60000) + " m\n\n"
                + "(R): registrar nuevo cliente.\n"
                + "(T): terminar simulación.\n\n").toUpperCase();
    }
    
    public void registrarCliente() {

        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del cliente nuevo.");
        Nodo nuevo = new Nodo(nombre, null);

        filaEspera.insertar(nuevo);

        tamañoMaximoFila();

        ingresarCaja();
    }

    public int ingresarCajaAleatoria() {
        estadoCaja4();
        boolean estados[] = {caja1.getDisponibilidad(),
            caja2.getDisponibilidad(), caja3.getDisponibilidad(),
            caja4.getDisponibilidad()};

        int disponibles[] = new int[4];

        int index = 0;

        for (int i = 0; i < estados.length; i++) {
            boolean caja = estados[i];
            if (caja == true) {
                disponibles[index++] = i + 1;
            }
        }

        int disponible = -1;

        while (disponible == -1) {

            Random random = new Random();
            int aleatorio = Math.abs(random.nextInt() % 5);

            for (int i = 0; i < index; i++) {
                if (aleatorio == disponibles[i]) {
                    disponible = disponibles[i];
                    break;
                }
            }

            if (index == 0) {
                break;
            }

        }
        return disponible;
    }

    public void ingresarCaja() {

        if (filaEspera.getPrimero() != null && disponibilidadCajas()) {

            long tiempoEspera = 0l;

            clientesAtendidos++;

            int numeroCaja = ingresarCajaAleatoria();

            if (numeroCaja != -1) {

                Nodo nuevo = filaEspera.eliminar();

                if (!disponibilidadCajas()) {
                    tamañoMaximoFila++;
                }

                System.out.println(nuevo.getNombre() + " salió de la fila de espera.\n");
                System.out.println(nuevo.getNombre() + " eligió la caja " + numeroCaja + "\n");

                if (numeroCaja == 1) {

                    tiempoEspera = (long) (90000l + Math.random() * 60000);
                    caja1.setDalay(tiempoEspera);
                    caja1.ingresarCliente(nuevo, 1, this);
                    System.out.println(nuevo.getNombre() + " ingresó en la caja 1.\n");
                } else if (numeroCaja == 2) {

                    tiempoEspera = (long) (120000l + Math.random() * 180000);
                    caja2.setDalay(tiempoEspera);
                    caja2.ingresarCliente(nuevo, 2, this);
                    System.out.println(nuevo.getNombre() + " ingresó en la caja 2.\n");
                } else if (numeroCaja == 3) {

                    tiempoEspera = (long) (120000l + Math.random() * 120000);
                    caja3.setDalay(tiempoEspera);
                    caja3.ingresarCliente(nuevo, 3, this);
                    System.out.println(nuevo.getNombre() + " ingresó en la caja 3.\n");
                } else if (numeroCaja == 4) {
                    tiempoEspera = (long) (0000l + Math.random() * 150000);
                    caja4.setDalay(tiempoEspera);
                    caja4.ingresarCliente(nuevo, 4, this);
                    tiempoOperandoCaja4 += tiempoEspera;
                    System.out.println(nuevo.getNombre() + " ingresó en la caja 4.\n");
                }

            }

        }
    }

    public boolean disponibilidadCajas() {
        if (caja1.getDisponibilidad()
                || caja2.getDisponibilidad()
                || caja3.getDisponibilidad()
                || caja4.getDisponibilidad()) {
            return true;
        }
        return false;
    }
    
    public void estadoCaja4() {

        if (filaEspera.getNumeroElementos() > 0 && estadoCaja4) {
            caja4.setDisponibilidad(true);
        } else if (filaEspera.getNumeroElementos() > 20 && !estadoCaja4) {
            caja4.setDisponibilidad(true);
            estadoCaja4 = true;
            System.out.println("caja 4 en acción.\n");
        } else {
            estadoCaja4 = false;
        }
    }
    
    public float filaMedia() {

        float filaTotal = 0;

        for (int i = 0; i <= elementosTamañoMedio; i++) {
            filaTotal += arrayTamañoMedioFila[i];
        }

        if (filaTotal == 0) {
            return 0;
        }

        return (filaTotal / elementosTamañoMedio);
    }

    public void agregarTamañoMedioFila() {
        
        if (filaEspera.getNumeroElementos() > 1) {
            tamañoMedioFila++;
            System.out.println("tamaño medio fila: " + tamañoMedioFila + ".\n");
        } else if(filaEspera.getNumeroElementos() == 1){
            arrayTamañoMedioFila[elementosTamañoMedio++] = tamañoMedioFila;
            tamañoMedioFila = 0;
            System.out.println("nuevo elemento agregado al array.\n");
        }
    }

    public void tamañoMaximoFila() {
        if (filaEspera.getNumeroElementos() > tamañoMaximoFila) {
            tamañoMaximoFila = filaEspera.getNumeroElementos();
        }
    }
    
    public void tiempoMaximoEspera(long tiempo) {

        if (filaEspera.getNumeroElementos() > 0) {
            tiempoMaximoEsperaAnterior += tiempo;
        } else {
            tiempoMaximoEsperaAnterior = 0;
        }

        if (tiempoMaximoEsperaAnterior > tiempoMaximoEspera) {
            tiempoMaximoEspera = tiempoMaximoEsperaAnterior;
        }
    }

}
