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
public class Termino implements Serializable {

    private String termino;
    private String descripcion;

    //Override temporar, para confirmar que los elementos de estén guardando bien en el arraylist
    @Override
    public String toString() {
        return "Termino{" +
               "termino='" + termino + '\'' +
               ", descripcion='" + descripcion + '\'' +
               '}';
    }
    
    public Termino(String palabra, String concepto) {
        this.termino = palabra;
        this.descripcion = concepto;
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
        //Termino TerminoLeido = new Termino();

        try {
            FileInputStream archivoTerminos = new FileInputStream("Terminos.pablo");
            ObjectInputStream input = new ObjectInputStream(archivoTerminos);
            Termino terminoLeido = (Termino) input.readObject();
            input.close();
            archivoTerminos.close();
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("Exepción: " + exception.getMessage());
        } /* finally {
            return TerminoLeido;
        }*/
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