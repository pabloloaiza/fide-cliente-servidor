package com.pabloloaiza.proyectofinal;

import java.io.Serializable;

/**
 *
 * @author Pablo Loaiza
 */
// Por ahora la orden solo guarda datos basicos para poder listarla.
// En iteraciones futuras el Cocinero podra completarla.
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    private int numero;
    private String descripcion;
    private String estado;

    public Orden(int numero, String descripcion, String estado) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }
}
