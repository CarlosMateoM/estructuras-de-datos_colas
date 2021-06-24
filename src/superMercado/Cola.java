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
public class Cola {

    private int numeroElementos;
    private Nodo primero;

    public Cola() {
        numeroElementos = 0;
        primero = null;
    }

    public int getNumeroElementos() {
        return numeroElementos;
    }

    private void setNumeroElementos(int numeroElementos) {
        this.numeroElementos = numeroElementos;
    }

    public Nodo getPrimero() {
        return primero;
    }

    public void setPrimero(Nodo primero) {
        this.primero = primero;
    }

    public Nodo getUltimo() {
        Nodo tem = getPrimero();
        while (tem.getSiguiente() != null) {
            tem = tem.getSiguiente();
        }
        return tem;
    }

    public void insertar(Nodo nuevo) {
        if (getPrimero() == null) {
            setPrimero(nuevo);
        } else {
            getUltimo().setSiguiente(nuevo);
        }
        int tem = getNumeroElementos();
        setNumeroElementos(++tem);
    }

    public Nodo eliminar() {
        Nodo tem = getPrimero();
        if (tem != null) {
            setPrimero(tem.getSiguiente());
            int temE = getNumeroElementos();
            setNumeroElementos(--temE);
        } 
        return tem;
    }

}
