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
    private String nombreCliente;

    public Orden(int numero, String descripcion, String estado, String nombreCliente) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.estado = estado;
        this.nombreCliente = nombreCliente;
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
    
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    
}
