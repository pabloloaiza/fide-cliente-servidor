/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pabloloaiza.proyectofinal;

/**
 *
 * @author Pablo Loaiza
 */
public class Cocinero extends Usuario {
    private double salario;
    private String horario;

    public Cocinero(String nombre, String contrasena, String tipo, double salario, String horario) {
        super(nombre, contrasena, tipo);
        this.salario = salario;
        this.horario = horario;
    }
}
