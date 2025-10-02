# Tienda de videojuegos PixelPlay
## Introduccion 
PixelPlay es una tienda ubicada en una galería en Wilson que buscó mejorar su gestión de bases de datos y expandirse a nivel de todo Lima por ello via este repositorio se explicará el apartado del **backend** que hemos empleado en el proyecto


Para el desarrollo de la página tenemos los siguientes objetivos

* [V] Conexión a base de datos
* [V] Login funcional 
* [X] Añadir productos al carrito
* [V]Registrarse
* [V]Poder desplazarse entre las vistas mediante botones colocados en el header
* [X]Validar método de pago
* [X]Seguridad

## Desarrollo 
### Conexión a base de datos :handshake:
Para poder emplear la conexión tuvimos que crear el bosquejo de la base de datos y por ello llegamos al siguiente diagrama entidad relación con lo cual se trabajará nuestro proyecto.


![Diagrama entidad Relacion](PIXELPLAY_BACK/src/img/Diagrama%20se%20base%20de%20datos.png)
### Registro Funcional :baby:
Se capturan los datos si se han escrito correctamente en los formatos solicitados dentro de nuestra base de datos.
### Login Funcional :walking_man:
Después de un registro exitoso , el login valida si el usuario existe en la base de datos , si esto es verdad , la vista cambiará a modo usuario y se habilitará la compra productos
