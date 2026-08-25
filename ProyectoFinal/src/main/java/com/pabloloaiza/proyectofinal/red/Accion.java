package com.pabloloaiza.proyectofinal.red;

/**
 *
 * @author Pablo Loaiza
 */
// Lista de operaciones que el cliente le puede pedir al servidor.
// Uso un enum en lugar de Strings para que el compilador avise si escribo mal una accion.
public enum Accion {
    INICIAR_SESION,
    REGISTRAR_USUARIO,
    LISTAR_PRODUCTOS,
    LISTAR_ARTICULOS,
    LISTAR_PRODUCTOS_DISPONIBLES,
    CREAR_PRODUCTO,
    EDITAR_PRODUCTO,
    CAMBIAR_DISPONIBILIDAD,
    CREAR_ORDEN,
    LISTAR_ORDENES_PENDIENTES,
    COMPLETAR_ORDEN,
    LISTAR_ORDENES_CLIENTE,
    GENERAR_FACTURA,
    CERRAR_SESION
}
