<%-- 
    Document   : DesplegarOfertas
    Created on : 25/05/2021, 02:27:15 PM
    Author     : R2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consumidor: Consultar ofertas</title>
        <link rel="stylesheet" href="css/estilo.css"/>
        <link rel="stylesheet" href="css/formularios.css"/>
    </head>
    <body>


        <form action="notificaciones" method="POST">
            <label for="mensaje">Aviso público:</label>
            <input type="text" name="mensaje"><br><br>
            <input id="solicitud" name ="solicitud" type="hidden" value="avisar">
            <input type="submit" value="submit">
        </form>

          
    </body>
</html>

