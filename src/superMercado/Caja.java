/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMercado;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author cmateo
 */
public class Caja {
    private boolean disponible;
    private long delay;
    
    public Caja(){
        disponible = true;
        delay = 0L;
    }
    
    public void setDalay(long delay){
        this.delay = delay;
    }
    
    public void setDisponibilidad(boolean disponible){
        this.disponible = disponible;
    }
    
    public boolean getDisponibilidad(){
        return disponible;
    }
    
    public void ingresarCliente(Nodo cliente, int numeroCaja, Controlador controlador){
        disponible = false;
        
        Timer timer = new Timer();
        TimerTask task;
        task = new TimerTask() {
            @Override
            public void run() {
                controlador.tiempoMaximoEspera(delay);
                controlador.agregarTamañoMedioFila();
                disponible = true;
                System.out.println("cliente: " + cliente.getNombre() + " salió de caja " + numeroCaja + ".\n"
                    + "factura: " + cliente.getFactura() + "\n");
                controlador.ingresarCaja();
                
            }
        };
        
        timer.schedule(task, delay);
    }
    
}
