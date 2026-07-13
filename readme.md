# Projecto final del curso Programación Cliente-Servidor Concurrente

Este documento presenta un resumen del objetivo del proyecto final para el curso Programación Cliente-Servidor Concurrente, así como un resumen de avances y cambios al diseño propuesto inicialmente (detallado en el archivo "PabloLoaiza_ProyectoFinalAvance1.pdf")

**Nota**: Con la idea de tener este trabajo como parte de un portafolio de proyectos a futuro, los commits se realizarán en ingles. Y eventualmente, este archivo, también será traducido a inglés con la idea de captar una audiencia más amplia. 

**Cambios con respecto al avance 1 (PDF):**

1. Cambios de nombre en interfaz y clase
 - Interfaz: ~~ProductoVendible~~ -> Producto
 - Clase: ~~Producto~~ -> Articulo

2. Cambio de ubicación de atributos
 - La interfaz Producto (antes denominada "ProductoVendible") ya no implementará los siguientes atributos. En cambio, los atributos se definirán en las clases que implementan la interfaz
  - Precio
  - Nombre
  - Descripción


| ID | Historia de usuario | Estado |
|----|---------------------|--------|
| HU1 | ~~Como nuevo usuario, necesito poder registrarme en el sistema, con el fin de poder realizar pedidos y ver mis puntos canjeables.~~ | Completado |
| HU2 | ~~Como cajero, necesito registrar productos o combos, con el fin de mantener el menú actualizado.~~ | Completado |
| HU3 | Como cajero, necesito editar productos agregados anteriormente, con el fin de mantener el menú actualizado. | No iniciado |
| HU4 | ~~Como cocinero, necesito recibir notificaciones de nuevos pedidos, con el fin de preparar adecuadamente las órdenes.~~ | Completado |
| HU5 | Como cocinero, necesito completar ordenes que ya se han realizado, con el fin de organizar el trabajo. | No iniciado |
| HU6 | Como cliente o cajero, necesito generar facturas de los pedidos realizados, con el fin de llevar un registro financiero. | No iniciado |
| HU7 | ~~Como usuario, necesito iniciar sesión en la aplicación, con el fin de realizar acciones en el sistema.~~ | Completado |
| HU8 | ~~Como usuario, necesito cerrar la sesión activa, con el fin de preservar la privacidad de mis datos.~~ | Completado |
| HU9 | Como cliente, necesito consultar mis ordenes, con el fin de llevar un registro de mis pedidos. | No iniciado |
| HU10 | Como cajero necesito marcar productos como agotados, con el fin de evitar ordenes en productos no disponibles.  | No iniciado |
