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
import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public class Diccionario implements Serializable {

    // Identificador de version para la serializacion
    private static final long serialVersionUID = 1L;

    private ArrayList<Termino> terminiosdiccionario;

    // Constructor vacio: inicializa la lista para evitar valores nulos
    public Diccionario() {
        this.terminiosdiccionario = new ArrayList<>();
    }

    public Diccionario(ArrayList<Termino> terminiosdiccionario) {
        this.terminiosdiccionario = terminiosdiccionario;
    }
    
    //Método para escribir un objeto a un archivo
    public static void EscribirDiccionario(Diccionario terminosDiccionario) {
        try {
            FileOutputStream archivoDiccionarios = new FileOutputStream("Diccionario.pablo");
            ObjectOutputStream output = new ObjectOutputStream(archivoDiccionarios);
            output.writeObject(terminosDiccionario);
            output.close();
            archivoDiccionarios.close();
        } catch (IOException exception) {
            System.out.println("Exepción: " + exception.getMessage());
        }
    }

    //Método para escribir un objeto a un archivo
    public static Diccionario LeerDiccionario() throws IOException, ClassNotFoundException {
        try {
            FileInputStream archivoDiccionarios = new FileInputStream("Diccionario.pablo");
            ObjectInputStream input = new ObjectInputStream(archivoDiccionarios);
            Diccionario diccionarioLeido = (Diccionario) input.readObject();
            input.close();
            archivoDiccionarios.close();
            return diccionarioLeido;
        } catch (IOException | ClassNotFoundException exception) {
            throw exception;
        }
    }

    public ArrayList<Termino> getTerminiosdiccionario() {
        return terminiosdiccionario;
    }

    public void setTerminiosdiccionario(ArrayList<Termino> terminiosdiccionario) {
        this.terminiosdiccionario = terminiosdiccionario;
    }    
}
