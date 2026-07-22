/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pabloloaiza.estudiocasopractico2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Pablo Loaiza
 */
// Se implementan las interfaces para serializar y ordenar la colección

public class Termino implements Serializable, Comparable<Termino> {
    // Identificador de version para la serializacion
    private static final long serialVersionUID = 1L;

    private String termino;
    private String descripcion;

    // Constructor vacio: requerido para crear objetos sin datos iniciales
    public Termino() {
    }

    public Termino(String palabra, String concepto) {
        this.termino = palabra;
        this.descripcion = concepto;
    }

    // Se ordena por la palabra, sin distinguir mayusculas/minusculas
    @Override
    public int compareTo(Termino otro) {
        return this.termino.compareToIgnoreCase(otro.termino);
    }

    // Override para mostrar el contenido del termino de forma legible (para pruebas)
    @Override
    public String toString() {
        return "Termino{" +
               "termino='" + termino + '\'' +
               ", descripcion='" + descripcion + '\'' +
               '}';
    }

    //Método para escribir un objeto a un archivo
    public static void EscribirTermino(Termino termino) {
        try {
            FileOutputStream archivoTerminos = new FileOutputStream("Terminos.pablo");
            ObjectOutputStream output = new ObjectOutputStream(archivoTerminos);
            output.writeObject(termino);
            output.close();
            archivoTerminos.close();
        } catch (IOException exception) {
            System.out.println("Exepción: " + exception.getMessage());
        }
    }

//Método para escribir un objeto a un archivo
    public static void LeerTermino() {
        try {
            FileInputStream archivoTerminos = new FileInputStream("Terminos.pablo");
            ObjectInputStream input = new ObjectInputStream(archivoTerminos);
            Termino terminoLeido = (Termino) input.readObject();
            input.close();
            archivoTerminos.close();
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("Exepción: " + exception.getMessage());
        }
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}