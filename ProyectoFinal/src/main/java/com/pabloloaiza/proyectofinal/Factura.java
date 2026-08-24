/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pabloloaiza.proyectofinal;

/**
 *
 * @author Pablo Loaiza
 */
public class Factura {
    private int numeroOrden;
    private String nombreCliente;
    private String productos;
    private String cantidadProductos;
    private int total; 

    public Factura(int numeroOrden, String nombreCliente, String productos, String cantidadProductos, int total) {
        this.numeroOrden = numeroOrden;
        this.nombreCliente = nombreCliente;
        this.productos = productos;
        this.cantidadProductos = cantidadProductos;
        this.total = total;
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(String cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
