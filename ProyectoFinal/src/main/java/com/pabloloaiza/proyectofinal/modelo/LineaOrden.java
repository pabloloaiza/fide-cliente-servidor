package com.pabloloaiza.proyectofinal.modelo;

import java.io.Serializable;

/**
 *
 * @author Pablo Loaiza
 */
// Un producto con su cantidad dentro de una orden (tabla orden_detalle).
// Copio nombre y precio para que la factura no cambie si el cajero edita el producto despues.
public class LineaOrden implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idProducto;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;

    public LineaOrden(int idProducto, String nombreProducto, int cantidad, double precioUnitario) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return cantidad + " x " + nombreProducto + " = $" + getSubtotal();
    }
}
