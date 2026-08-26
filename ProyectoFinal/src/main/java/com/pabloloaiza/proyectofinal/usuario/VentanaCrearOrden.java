package com.pabloloaiza.proyectofinal.usuario;

import com.pabloloaiza.proyectofinal.modelo.Cliente;
import com.pabloloaiza.proyectofinal.modelo.LineaOrden;
import com.pabloloaiza.proyectofinal.modelo.Orden;
import com.pabloloaiza.proyectofinal.modelo.Producto;
import com.pabloloaiza.proyectofinal.red.Accion;
import com.pabloloaiza.proyectofinal.red.Respuesta;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Pablo Loaiza
 */
// El cliente arma su pedido. La lista de la izquierda solo trae productos
// DISPONIBLES, por eso los que el cajero marco como agotados no aparecen.
public class VentanaCrearOrden extends JFrame {

    private final Cliente cliente;
    private final ConexionServidor conexion;

    private final DefaultListModel<Producto> modeloMenu = new DefaultListModel<>();
    private final JList<Producto> listaMenu = new JList<>(modeloMenu);
    private final DefaultListModel<LineaOrden> modeloCarrito = new DefaultListModel<>();
    private final JList<LineaOrden> listaCarrito = new JList<>(modeloCarrito);
    private final JLabel etiquetaTotal = new JLabel("Total: $0.0");

    public VentanaCrearOrden(Cliente cliente, ConexionServidor conexion) {
        this.cliente = cliente;
        this.conexion = conexion;

        setTitle("Hacer Pedido");
        setSize(680, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listaMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel panelListas = new JPanel(new GridLayout(1, 2));
        JPanel panelMenu = new JPanel(new BorderLayout());
        panelMenu.add(new JLabel("Menu disponible"), BorderLayout.NORTH);
        panelMenu.add(new JScrollPane(listaMenu), BorderLayout.CENTER);
        JPanel panelCarrito = new JPanel(new BorderLayout());
        panelCarrito.add(new JLabel("Mi pedido"), BorderLayout.NORTH);
        panelCarrito.add(new JScrollPane(listaCarrito), BorderLayout.CENTER);
        panelListas.add(panelMenu);
        panelListas.add(panelCarrito);
        add(panelListas, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 5));
        JButton botonAgregar = new JButton("Agregar >>");
        JButton botonQuitar = new JButton("Quitar");
        JButton botonRefrescar = new JButton("Refrescar Menu");
        JButton botonConfirmar = new JButton("Confirmar Pedido");
        panelBotones.add(botonAgregar);
        panelBotones.add(botonQuitar);
        panelBotones.add(botonRefrescar);
        panelBotones.add(botonConfirmar);
        panelBotones.add(etiquetaTotal);
        add(panelBotones, BorderLayout.SOUTH);

        botonAgregar.addActionListener(e -> agregarAlCarrito());
        botonQuitar.addActionListener(e -> quitarDelCarrito());
        botonRefrescar.addActionListener(e -> cargarMenu());
        botonConfirmar.addActionListener(e -> confirmarPedido());

        cargarMenu();
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void cargarMenu() {
        Respuesta respuesta = conexion.enviar(Accion.LISTAR_PRODUCTOS_DISPONIBLES);
        if (!respuesta.isExito()) {
            JOptionPane.showMessageDialog(this, respuesta.getMensaje());
            return;
        }
        modeloMenu.clear();
        for (Producto producto : (ArrayList<Producto>) respuesta.getContenido()) {
            modeloMenu.addElement(producto);
        }
    }

    // Si el producto ya estaba en el carrito solo le sumo la cantidad,
    // en lugar de meter dos lineas iguales.
    private void agregarAlCarrito() {
        Producto producto = listaMenu.getSelectedValue();
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto del menu.");
            return;
        }
        String textoCantidad = JOptionPane.showInputDialog(this, "Cantidad:", "1");
        if (textoCantidad == null) {
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(textoCantidad.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un numero entero.");
            return;
        }
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.");
            return;
        }

        for (int i = 0; i < modeloCarrito.size(); i++) {
            LineaOrden linea = modeloCarrito.get(i);
            if (linea.getIdProducto() == producto.getId()) {
                linea.setCantidad(linea.getCantidad() + cantidad);
                modeloCarrito.set(i, linea);
                actualizarTotal();
                return;
            }
        }
        modeloCarrito.addElement(new LineaOrden(producto.getId(), producto.getNombre(),
                cantidad, producto.getPrecio()));
        actualizarTotal();
    }

    private void quitarDelCarrito() {
        int seleccion = listaCarrito.getSelectedIndex();
        if (seleccion < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una linea de tu pedido.");
            return;
        }
        modeloCarrito.remove(seleccion);
        actualizarTotal();
    }

    private void actualizarTotal() {
        double total = 0;
        for (int i = 0; i < modeloCarrito.size(); i++) {
            total += modeloCarrito.get(i).getSubtotal();
        }
        etiquetaTotal.setText("Total: $" + total);
    }

    private void confirmarPedido() {
        if (modeloCarrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tu pedido esta vacio.");
            return;
        }
        ArrayList<LineaOrden> lineas = new ArrayList<>();
        for (int i = 0; i < modeloCarrito.size(); i++) {
            lineas.add(modeloCarrito.get(i));
        }

        Orden orden = new Orden(cliente.getId(), cliente.getNombre(), lineas);
        Respuesta respuesta = conexion.enviar(Accion.CREAR_ORDEN, orden);
        JOptionPane.showMessageDialog(this, respuesta.getMensaje());
        if (respuesta.isExito()) {
            dispose();
        } else {
            // Pudo agotarse un producto mientras armaba el pedido, refresco el menu
            cargarMenu();
        }
    }
}
