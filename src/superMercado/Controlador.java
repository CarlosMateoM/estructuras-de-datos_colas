/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMercado;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

/**
 *
 * @author cmateo
 */
public class Controlador {

    Cola filaEspera = new Cola();
    Cola caja1 = new Cola();
    Cola caja2 = new Cola();
    Cola caja3 = new Cola();
    Cola caja4 = new Cola();

    private boolean estadoCaja1 = true, estadoCaja2 = true,
            estadoCaja3 = true, estadoCaja4 = false, estadoCaja4_20 = false;
    private int clientesAtendidos = 0, tamañoMaximoFila = 0,
            tamañoMedioFila = 0, abiertaCaja4 = 0, tiempoMaximoEspera = 0;
    private int arrayTamañoMedioFila[] = new int[100];
    private int elementosTamañoMedio = -1;

    public Controlador() {
        String op = "";
        while (true) {
            op = menu();

            if (op.equals("R")) {
                autoRegistro();
            } else if(op.equals("1"))
                sacarC1();
            else if (op.equals("T")) {
                System.exit(0);
                break;
            }
        }
    }
    
    public void autoRegistro(){
        for(int i = 0; i < 24; i++){
            registrarCliente(String.valueOf(i));
        }
    }
    
    public void sacarC1(){
        salirCaja(0l,caja1,1);
        ingresarCaja(filaEspera);
    }

    public String menu() {
        return JOptionPane.showInputDialog(null, "Disponibilidad\n" + "-Caja 1: ( " + estadoCaja1
                + " )\n-Caja 2: ( " + estadoCaja2 + " )\n-Caja 3: ( " + estadoCaja3
                + " )\n-Caja 4: ( " + estadoCaja4 + " )\n"
                + "fila espera: " + filaEspera.getNumeroElementos() + " clientes.\n"
                + "clientes atendidos: " + clientesAtendidos + "\n"
                + "(R): registrar nuevo cliente.\n"
                + "(T): terminar simulación\n").toUpperCase();
    }

    public void salirCaja(long delay, Cola caja, int estado) {

        Timer timer = new Timer();
        TimerTask task;
        task = new TimerTask() {
            @Override
            public void run() {
                if (caja.getPrimero() != null) {
                    
                    Nodo tem = caja.eliminar();
                    System.out.println("cliente: " + tem.getNombre() + " salió de caja.\n"
                            + "factura: " + tem.getFactura() + "\n");
                    
                    switch (estado) {
                        case 1:
                            estadoCaja1 = true;
                            break;
                        case 2:
                            estadoCaja2 = true;
                            break;
                        case 3:
                            estadoCaja3 = true;
                            break;
                        case 4:
                            estadoCaja4();
                            break;
                    }
                    clientesAtendidos++;
                    ingresarCaja(filaEspera);
                    System.gc();
                }else
                    System.out.println("no hay nadie en caja.\n");
            }
        };

        timer.schedule(task, delay);

    }

    public int ingresarCajaAleatoria() {
        boolean estados[] = {estadoCaja1, estadoCaja2, estadoCaja3, estadoCaja4};
        int disponibles[] = new int[3];

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
            
            if(index == 0) break;

        }
        return disponible;
    }

    public void ingresarCaja(Cola filaEspera) {

        if (filaEspera.getPrimero() != null) {

            if (estadoCaja1) {
                
                estadoCaja1 = false;
                Nodo nuevo = filaEspera.eliminar();
                System.out.println(nuevo.getNombre() + " salió de la fila de espera.\n");
                caja1.insertar(nuevo);
                System.out.println(nuevo.getNombre() + " ingresó en la caja 1.\n");
                salirCaja(1000L, caja1, 1);
            } else {

                int numeroCaja = ingresarCajaAleatoria();
                
                estadoCaja4();
                
                if (numeroCaja != -1) {
                    Nodo nuevo = filaEspera.eliminar();
                    System.out.println(nuevo.getNombre() + " salió de la fila de espera.\n");
                    System.out.println(nuevo.getNombre() + " eligió la caja " + numeroCaja + "\n");

                    if (numeroCaja == 2) {
                        estadoCaja2 = false;
                        caja2.insertar(nuevo);
                        System.out.println(nuevo.getNombre() + " ingresó en la caja 2.\n");
                        salirCaja(1000L, caja2, 2);
                    } else if (numeroCaja == 3) {
                        estadoCaja3 = false;
                        caja3.insertar(nuevo);
                        System.out.println(nuevo.getNombre() + " ingresó en la caja 3.\n");
                        salirCaja(1000L, caja3, 3);
                    } else if (numeroCaja == 4) {
                        estadoCaja4 = false;
                        caja4.insertar(nuevo);
                        System.out.println(nuevo.getNombre() + " ingresó en la caja 4.\n");
                        salirCaja(1000L, caja4, 4);
                    }
                    
                }
            }
        }
    }

    public void estadoCaja4() {
        //true true = entro
        //false false = no entro
        if (filaEspera.getNumeroElementos() > 20 && !estadoCaja4_20) {
            estadoCaja4 = true;
            estadoCaja4_20 = true;
            System.out.println("caja 4 en acción.\n");
            //true 
        } else if (filaEspera.getNumeroElementos() > 0 && estadoCaja4_20) {
            estadoCaja4 = true;
        } else {
            estadoCaja4_20 = false;
        }
    }

    public void registrarCliente(String nom) {

        String nombre = nom;//JOptionPane.showInputDialog(null, "Ingrese el nombre del cliente nuevo.");
        Nodo nuevo = new Nodo(nombre, null);

        if (clientesAtendidos > 0) {
            if (filaEspera.getPrimero() == null) {
                arrayTamañoMedioFila[++elementosTamañoMedio] = tamañoMedioFila;
                tamañoMedioFila = 0;
            }
        }

        filaEspera.insertar(nuevo);
        ingresarCaja(filaEspera);
                
        tamañoMaximoFila++;

    }
}
