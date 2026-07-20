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
import static java.lang.System.in;
import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public class Diccionario implements Serializable {

    private ArrayList<Termino> terminiosdiccionario;

    //Constructor vacio, para cuando se lee el archivo
    public Diccionario() {}
    
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
    public static void LeerDiccionario() {
        try {
            FileInputStream archivoDiccionarios = new FileInputStream("Diccionario.pablo");
            ObjectInputStream input = new ObjectInputStream(archivoDiccionarios);
            Diccionario diccionarioLeido = (Diccionario) input.readObject();
//            terminiosdiccionario = (ArrayList<Termino>) in.readObject();
            input.close();
            archivoDiccionarios.close();
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("Exepción: " + exception.getMessage());
        }
    }

    public ArrayList<Termino> getTerminiosdiccionario() {
        return terminiosdiccionario;
    }

    public void setTerminiosdiccionario(ArrayList<Termino> terminiosdiccionario) {
        this.terminiosdiccionario = terminiosdiccionario;
    }    
}
