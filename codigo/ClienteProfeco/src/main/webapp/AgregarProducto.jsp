<%-- 
    Document   : Prueba
    Created on : May 22, 2021, 12:03:31 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comercio: Subir producto</title>
    </head>
    <body>
        <h2>Subir producto</h2>
        <form action="mercado" method="POST">
            <label for="comercio">Comercio:</label>
            <input type="text" name="comercio"><br><br>
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre"><br><br>
            <label for="precio">Precio:</label>
            <input type="text" name="precio"><br><br>
            <label for="oferta">Oferta:</label>
            <input type="text" name="oferta"><br><br>
            <input id="solicitud" name ="solicitud" type="hidden" value="subirProducto">
            <input type="submit" value="submit">
        </form>
    </body>
</html>
