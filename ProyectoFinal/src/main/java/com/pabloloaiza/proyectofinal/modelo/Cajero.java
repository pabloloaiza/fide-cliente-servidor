package com.pabloloaiza.proyectofinal.modelo;

/**
 *
 * @author Pablo Loaiza
 */
public class Cajero extends Usuario {

    private static final long serialVersionUID = 2L;

    private double salario;
    private String horario;

    public Cajero(int id, String nombre, String contrasena, double salario, String horario) {
        super(id, nombre, contrasena, "Cajero");
        this.salario = salario;
        this.horario = horario;
    }

    public double getSalario() {
        return salario;
    }

    public String getHorario() {
        return horario;
    }
}
