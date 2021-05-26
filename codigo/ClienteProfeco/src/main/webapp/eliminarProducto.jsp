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
            <label for="id">Id:</label>
            <input type="text" name="id"><br><br>
            <input id="solicitud" name ="solicitud" type="hidden" value="eliminarProducto">
            <input type="submit" value="submit">
        </form>
    </body>
</html>
