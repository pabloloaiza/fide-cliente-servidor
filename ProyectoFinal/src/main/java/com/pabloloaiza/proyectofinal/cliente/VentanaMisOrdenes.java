package com.pabloloaiza.proyectofinal.cliente;

import com.pabloloaiza.proyectofinal.modelo.Cliente;
import com.pabloloaiza.proyectofinal.modelo.Factura;
import com.pabloloaiza.proyectofinal.modelo.Orden;
import com.pabloloaiza.proyectofinal.red.Accion;
import com.pabloloaiza.proyectofinal.red.Respuesta;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Pablo Loaiza
 */
// Casos de uso del cliente: ver sus ordenes y generar la factura de una de ellas.
public class VentanaMisOrdenes extends JFrame {

    private final Cliente cliente;
    private final ConexionServidor conexion;
    private final DefaultListModel<Orden> modelo = new DefaultListModel<>();
    private final JList<Orden> lista = new JList<>(modelo);

    public VentanaMisOrdenes(Cliente cliente, ConexionServidor conexion) {
        this.cliente = cliente;
        this.conexion = conexion;

        setTitle("Mis Ordenes - " + cliente.getNombre());
        setSize(580, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 3));
        JButton botonDetalle = new JButton("Ver Detalle");
        JButton botonFactura = new JButton("Generar Factura");
        JButton botonRefrescar = new JButton("Refrescar");
        panelBotones.add(botonDetalle);
        panelBotones.add(botonFactura);
        panelBotones.add(botonRefrescar);
        add(panelBotones, BorderLayout.SOUTH);

        botonDetalle.addActionListener(e -> verDetalle());
        botonFactura.addActionListener(e -> generarFactura());
        botonRefrescar.addActionListener(e -> cargarOrdenes());

        cargarOrdenes();
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void cargarOrdenes() {
        Respuesta respuesta = conexion.enviar(Accion.LISTAR_ORDENES_CLIENTE, cliente.getId());
        if (!respuesta.isExito()) {
            JOptionPane.showMessageDialog(this, respuesta.getMensaje());
            return;
        }
        modelo.clear();
        for (Orden orden : (ArrayList<Orden>) respuesta.getContenido()) {
            modelo.addElement(orden);
        }
        if (modelo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todavia no tienes ordenes registradas.");
        }
    }

    private void verDetalle() {
        Orden orden = lista.getSelectedValue();
        if (orden == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una orden.");
            return;
        }
        StringBuilder detalle = new StringBuilder("Orden #" + orden.getId()
                + " (" + orden.getEstado() + ")\nFecha: " + orden.getFecha() + "\n\n");
        orden.getLineas().forEach(linea -> detalle.append(linea).append("\n"));
        detalle.append("\nTotal: $").append(orden.getTotal());
        JOptionPane.showMessageDialog(this, detalle.toString());
    }

    private void generarFactura() {
        Orden orden = lista.getSelectedValue();
        if (orden == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una orden.");
            return;
        }
        Respuesta respuesta = conexion.enviar(Accion.GENERAR_FACTURA, orden.getId());
        if (!respuesta.isExito()) {
            JOptionPane.showMessageDialog(this, respuesta.getMensaje());
            return;
        }
        mostrarFactura((Factura) respuesta.getContenido());
    }

    // Muestro la factura en un JTextArea con fuente monoespaciada para que
    // las columnas queden alineadas como en un recibo real.
    private void mostrarFactura(Factura factura) {
        JTextArea area = new JTextArea(factura.comoTexto());
        area.setEditable(false);
        area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Factura", JOptionPane.INFORMATION_MESSAGE);
    }
}
