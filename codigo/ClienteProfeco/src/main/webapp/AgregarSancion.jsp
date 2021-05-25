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
        <title>Prefeco: Sancionar</title>
    </head>
    <body>
        <h2>Sancionar</h2>
        <form action="profeco" method="POST">
            <label for="comercio">Comercio:</label>
            <input type="text" name="comercio" type="number"><br><br>
            <label for="asunto">Asunto:</label>
            <input type="text" name="asunto"><br><br>
            <label for="descripcion">Descripción:</label>
            <input type="text" name="descripcion"><br><br>
            <label for="castigos">Castigos:</label>
            <input type="text" name="castigos"><br><br> 
            <label for="multa">Multa:</label>
            <input type="text" name="multa" type"number"><br><br>
            <input id="solicitud" name ="solicitud" type="hidden" value="subirSancion">
            <input type="submit" value="submit">
        </form>
    </body>
</html>
