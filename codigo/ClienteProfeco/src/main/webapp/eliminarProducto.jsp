<%-- 
    Document   : eliminarProducto
    Created on : May 24, 2021, 12:41:17 PM
    Author     : joelvalenzuela
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comercio: Eliminar producto</title>
    </head>
    <body>
        <h2>Eliminar producto</h2>
        <form action="mercado" method="POST">
            <label for="comercio">Comercio:</label>
            <input type="text" name="comercio"><br><br>
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre"><br><br>
            <input id="solicitud" name ="solicitud" type="hidden" value="eliminarProducto">
            <input type="submit" value="submit">
        </form>
    </body>
</html>
