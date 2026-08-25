package com.pabloloaiza.proyectofinal.modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public class Orden implements Serializable {

    private static final long serialVersionUID = 2L;

    public static final String PENDIENTE = "PENDIENTE";
    public static final String COMPLETADA = "COMPLETADA";

    private int id;
    private int idCliente;
    private String nombreCliente;
    private String estado;
    private String fecha;
    private double total;
    private ArrayList<LineaOrden> lineas;

    public Orden(int id, int idCliente, String nombreCliente, String estado,
            String fecha, double total, ArrayList<LineaOrden> lineas) {
        this.id = id;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.estado = estado;
        this.fecha = fecha;
        this.total = total;
        this.lineas = lineas;
    }

    // Constructor para una orden nueva: el id y la fecha los pone el servidor
    public Orden(int idCliente, String nombreCliente, ArrayList<LineaOrden> lineas) {
        this(0, idCliente, nombreCliente, PENDIENTE, "", 0, lineas);
        this.total = calcularTotal();
    }

    public final double calcularTotal() {
        double suma = 0;
        for (LineaOrden linea : lineas) {
            suma += linea.getSubtotal();
        }
        return suma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<LineaOrden> getLineas() {
        return lineas;
    }

    // Texto corto para listar la orden en un JList o JTextArea
    public String resumen() {
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < lineas.size(); i++) {
            if (i > 0) {
                texto.append(", ");
            }
            texto.append(lineas.get(i).getCantidad()).append("x").append(lineas.get(i).getNombreProducto());
        }
        return texto.toString();
    }

    @Override
    public String toString() {
        return "Orden #" + id + " [" + estado + "] " + nombreCliente + " - " + resumen() + " - $" + total;
    }
}
