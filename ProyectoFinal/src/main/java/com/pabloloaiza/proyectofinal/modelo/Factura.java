package com.pabloloaiza.proyectofinal.modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public class Factura implements Serializable {

    private static final long serialVersionUID = 2L;

    public static final double PORCENTAJE_IMPUESTO = 0.13; // IVA de Costa Rica

    private int id;
    private int idOrden;
    private String nombreCliente;
    private double subtotal;
    private double impuesto;
    private double total;
    private String fecha;
    private ArrayList<LineaOrden> lineas;

    public Factura(int id, int idOrden, String nombreCliente, double subtotal,
            double impuesto, double total, String fecha, ArrayList<LineaOrden> lineas) {
        this.id = id;
        this.idOrden = idOrden;
        this.nombreCliente = nombreCliente;
        this.subtotal = subtotal;
        this.impuesto = impuesto;
        this.total = total;
        this.fecha = fecha;
        this.lineas = lineas;
    }

    public int getId() {
        return id;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public double getTotal() {
        return total;
    }

    public String getFecha() {
        return fecha;
    }

    public ArrayList<LineaOrden> getLineas() {
        return lineas;
    }

    // Representacion imprimible de la factura para mostrarla en la ventana del cliente
    public String comoTexto() {
        StringBuilder texto = new StringBuilder();
        texto.append("========== FACTURA #").append(id).append(" ==========\n");
        texto.append("Orden:   #").append(idOrden).append("\n");
        texto.append("Cliente: ").append(nombreCliente).append("\n");
        texto.append("Fecha:   ").append(fecha).append("\n");
        texto.append("-----------------------------------------\n");
        for (LineaOrden linea : lineas) {
            texto.append(linea.getCantidad()).append(" x ")
                    .append(linea.getNombreProducto())
                    .append(" ($").append(linea.getPrecioUnitario()).append(")")
                    .append("   $").append(linea.getSubtotal()).append("\n");
        }
        texto.append("-----------------------------------------\n");
        texto.append("Subtotal:  $").append(subtotal).append("\n");
        texto.append("Impuesto:  $").append(impuesto).append("\n");
        texto.append("TOTAL:     $").append(total).append("\n");
        return texto.toString();
    }
}
