package com.pabloloaiza.proyectofinal.red;

import java.io.Serializable;

/**
 *
 * @author Pablo Loaiza
 */
// Objeto que el cliente envia por el socket: que quiere hacer y con que datos.
// Los datos van en un arreglo de Object porque cada accion necesita cosas distintas.
public class Peticion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Accion accion;
    private Object[] datos;

    public Peticion(Accion accion, Object... datos) {
        this.accion = accion;
        this.datos = datos;
    }

    public Accion getAccion() {
        return accion;
    }

    public Object[] getDatos() {
        return datos;
    }

    // Atajos para leer los parametros sin castear en cada DAO
    public String textoEn(int posicion) {
        return (String) datos[posicion];
    }

    public int enteroEn(int posicion) {
        return (Integer) datos[posicion];
    }

    public boolean booleanoEn(int posicion) {
        return (Boolean) datos[posicion];
    }

    public Object objetoEn(int posicion) {
        return datos[posicion];
    }
}
