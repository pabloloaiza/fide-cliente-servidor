package com.pabloloaiza.proyectofinal.cliente;

import com.pabloloaiza.proyectofinal.modelo.Cocinero;
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
import javax.swing.ListSelectionModel;

/**
 *
 * @author Pablo Loaiza
 */
// Caso de uso del cocinero: ver las ordenes pendientes y completarlas.
// Un hilo aparte refresca la lista cada 5 segundos para ver las ordenes
// que los clientes van creando desde otras conexiones.
public class VentanaCocinero extends JFrame {

    private static final int SEGUNDOS_REFRESCO = 5;

    private final ConexionServidor conexion;
    private final DefaultListModel<Orden> modelo = new DefaultListModel<>();
    private final JList<Orden> lista = new JList<>(modelo);
    private volatile boolean ventanaAbierta = true;

    public VentanaCocinero(Cocinero cocinero, ConexionServidor conexion) {
        this.conexion = conexion;

        setTitle("Cocinero - " + cocinero.getNombre());
        setSize(560, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 3));
        JButton botonDetalle = new JButton("Ver Detalle");
        JButton botonCompletar = new JButton("Completar Orden");
        JButton botonCerrarSesion = new JButton("Cerrar Sesion");
        panelBotones.add(botonDetalle);
        panelBotones.add(botonCompletar);
        panelBotones.add(botonCerrarSesion);
        add(panelBotones, BorderLayout.SOUTH);

        botonDetalle.addActionListener(e -> verDetalle());
        botonCompletar.addActionListener(e -> completarOrden());
        botonCerrarSesion.addActionListener(e -> {
            ventanaAbierta = false;
            Utilidades.cerrarSesion(conexion);
        });

        cargarOrdenes();
        iniciarRefrescoAutomatico();
        setVisible(true);
    }

    // Hilo demonio: mientras la ventana este abierta vuelve a pedir las ordenes.
    // Es demonio para que no impida que la aplicacion se cierre.
    private void iniciarRefrescoAutomatico() {
        Thread hiloRefresco = new Thread(() -> {
            while (ventanaAbierta) {
                try {
                    Thread.sleep(SEGUNDOS_REFRESCO * 1000L);
                } catch (InterruptedException ex) {
                    return;
                }
                if (ventanaAbierta) {
                    javax.swing.SwingUtilities.invokeLater(this::cargarOrdenes);
                }
            }
        });
        hiloRefresco.setDaemon(true);
        hiloRefresco.setName("Refresco-Cocina");
        hiloRefresco.start();
    }

    @SuppressWarnings("unchecked")
    private void cargarOrdenes() {
        Respuesta respuesta = conexion.enviar(Accion.LISTAR_ORDENES_PENDIENTES);
        if (!respuesta.isExito()) {
            return;
        }
        int seleccion = lista.getSelectedIndex();
        modelo.clear();
        for (Orden orden : (ArrayList<Orden>) respuesta.getContenido()) {
            modelo.addElement(orden);
        }
        if (seleccion >= 0 && seleccion < modelo.size()) {
            lista.setSelectedIndex(seleccion);
        }
    }

    private void verDetalle() {
        Orden orden = lista.getSelectedValue();
        if (orden == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una orden.");
            return;
        }
        StringBuilder detalle = new StringBuilder("Orden #" + orden.getId() + "\n");
        detalle.append("Cliente: ").append(orden.getNombreCliente()).append("\n");
        detalle.append("Fecha: ").append(orden.getFecha()).append("\n\n");
        orden.getLineas().forEach(linea -> detalle.append(linea).append("\n"));
        detalle.append("\nTotal: $").append(orden.getTotal());
        JOptionPane.showMessageDialog(this, detalle.toString());
    }

    private void completarOrden() {
        Orden orden = lista.getSelectedValue();
        if (orden == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una orden.");
            return;
        }
        Respuesta respuesta = conexion.enviar(Accion.COMPLETAR_ORDEN, orden.getId());
        JOptionPane.showMessageDialog(this, respuesta.getMensaje());
        cargarOrdenes();
    }
}
