/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pabloloaiza.proyectofinal;

/**
 *
 * @author Pablo Loaiza
 */
public class Cliente extends Usuario {
    
    private double puntosAcumulados;
    
    public Cliente(String nombre, String contrasena, String tipo, double puntosAcumulados) {
        super(nombre, contrasena, tipo);
        this.puntosAcumulados = puntosAcumulados;
    }

    public double getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(double puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }
}
