/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMercado;

/**
 *
 * @author cmateo
 */
public class Nodo {
    private String nombre;
    private Nodo siguiente;

    public Nodo(String nombre, Nodo siguiente) {
        this.nombre = nombre;
        this.siguiente = siguiente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
    public int getFactura(){
        return (int) (Math.random()*25000);
    }
    
    
}
