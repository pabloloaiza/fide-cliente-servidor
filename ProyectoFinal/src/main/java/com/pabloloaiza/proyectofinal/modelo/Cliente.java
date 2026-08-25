package com.pabloloaiza.proyectofinal.modelo;

/**
 *
 * @author Pablo Loaiza
 */
public class Cliente extends Usuario {

    private static final long serialVersionUID = 2L;

    private double puntosAcumulados;

    public Cliente(int id, String nombre, String contrasena, double puntosAcumulados) {
        super(id, nombre, contrasena, "Cliente");
        this.puntosAcumulados = puntosAcumulados;
    }

    public double getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(double puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }
}
