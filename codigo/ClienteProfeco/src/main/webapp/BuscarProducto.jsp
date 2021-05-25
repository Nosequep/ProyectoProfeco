<%-- 
    Document   : BuscarProducto
    Created on : May 25, 2021, 11:18:47 AM
    Author     : joelvalenzuela
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BuscarProducto</title>
    </head>
    <body>
        <h2>Buscar producto</h2>
        <form action="consumidor" method="POST">
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre"><br><br>
            <input id="solicitud" name ="solicitud" type="hidden" value="buscarProducto">
            <input type="submit" value="submit">
        </form>
    </body>
</html>
