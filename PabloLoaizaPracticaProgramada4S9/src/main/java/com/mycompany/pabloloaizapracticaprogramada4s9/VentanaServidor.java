/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Pablo Loaiza
 */
public class VentanaServidor extends JFrame{
    
    private JTextArea txtLog;
    private JButton botonIniciar;
    
    public VentanaServidor() {       
        setTitle("Servidor");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        txtLog = new JTextArea();
        txtLog.setEditable(false);

        JScrollPane scroll = new JScrollPane(txtLog);

        botonIniciar = new JButton("Iniciar Servidor");

        add(scroll, BorderLayout.CENTER);
        add(botonIniciar, BorderLayout.SOUTH);

        botonIniciar.addActionListener(e -> iniciarServidor());

        setVisible(true);
    }
    
}
