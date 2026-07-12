/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pabloloaiza.proyectofinal;

import java.awt.*;
import javax.swing.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

/**
 *
 * @author Pablo Loaiza
 */
public class VentanaCrearUsuario extends JFrame{

    private JTextField textoUsuario;
    private JPasswordField textoContrasena;
    private JButton botonGuardar;

    // Constructor
    public VentanaCrearUsuario() {
        setTitle("Crear Usuario");
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        //Botones
        add(new JLabel("Usuario:"));
        textoUsuario = new JTextField();
        add(textoUsuario);

        add(new JLabel("Contraseña:"));
        textoContrasena = new JPasswordField();
        add(textoContrasena);
        
        add(new JLabel("Rol:"));
        JRadioButton opcionCliente = new JRadioButton("Cliente");
        add(opcionCliente);
        JRadioButton opcionCajero = new JRadioButton("Cajero");
        add(opcionCajero);
        JRadioButton opcionCocinero = new JRadioButton("Cocinero");
        add(opcionCocinero);
        
        botonGuardar = new JButton("Guardar");
        add(botonGuardar);

        botonGuardar.addActionListener(e -> guardarUsuario());
        setVisible(true);
    }

    private void guardarUsuario() {
        try {
            FileOutputStream archivoUsuarios = new FileOutputStream("Contactos.extension");
            ObjectOutputStream output = new ObjectOutputStream(archivoUsuarios);
            //output.writeObject(usuario);
            output.close();
            archivoUsuarios.close();
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
