# Proyecto Final - Sistema de pedidos cliente/servidor

Aplicacion Java (Swing + Sockets + MySQL) con tres roles: Cajero, Cocinero y Cliente.

## Arquitectura

```
cliente/   ventanas Swing + ConexionServidor (socket)
red/       Peticion, Respuesta, Accion  (objetos que viajan por el socket)
servidor/  Servidor + HiloCliente + DAOs + ConexionBD (unico que habla con MySQL)
modelo/    clases de datos compartidas por cliente y servidor
```

El cliente **nunca** se conecta a MySQL: manda una `Peticion` por el socket y el
servidor le contesta con una `Respuesta`. El servidor crea un **hilo por cliente**,
por eso varios usuarios pueden iniciar sesion y trabajar al mismo tiempo.

## Pasos para ejecutar

1. Crear la base de datos (una sola vez):

   ```
   mysql -u root -p < schema.sql
   ```

2. Poner el usuario y la contrasena de MySQL en
   `src/main/java/com/pabloloaiza/proyectofinal/servidor/ConexionBD.java`.

3. Arrancar el **servidor** (debe quedar corriendo):

   ```
   mvn compile
   mvn exec:java -Dexec.mainClass=com.pabloloaiza.proyectofinal.servidor.Servidor
   ```

4. Arrancar uno o varios **clientes** (cada uno es un usuario distinto):

   ```
   mvn exec:java
   ```

   En NetBeans: click derecho en `Servidor.java` -> Run File, y luego
   Run Project (o Run File en `ProyectoFinal.java`) las veces que se quiera.

## Casos de uso implementados

| Rol | Caso de uso | Donde |
|-----|-------------|-------|
| Cajero | Editar productos ya creados | Administrar Menu -> Editar |
| Cajero | Marcar producto como agotado | Administrar Menu -> Marcar Agotado |
| Cocinero | Ver y completar ordenes | Ventana Cocinero (se refresca sola cada 5 s) |
| Cliente | Ver sus ordenes | Mis Ordenes |
| Cliente | Generar factura | Mis Ordenes -> Generar Factura |
| Todos | Login simultaneo | un hilo por socket en `Servidor` |

`GestorArchivos` y los archivos `.extension` quedaron de la entrega anterior y ya
no se usan; toda la persistencia es MySQL.
