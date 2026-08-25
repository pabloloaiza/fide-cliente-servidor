package com.pabloloaiza.proyectofinal.modelo;

/**
 *
 * @author Pablo Loaiza
 */
public class Cocinero extends Usuario {

    private static final long serialVersionUID = 2L;

    private double salario;
    private String horario;

    public Cocinero(int id, String nombre, String contrasena, double salario, String horario) {
        super(id, nombre, contrasena, "Cocinero");
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
