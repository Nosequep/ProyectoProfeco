<%-- 
    Document   : CalificarProducto
    Created on : May 25, 2021, 2:23:39 PM
    Author     : joelvalenzuela
--%>
<%@page import="com.profeco.entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Calificar producto</title>
    </head>
    <body>
        <%Producto producto = (Producto) request.getAttribute("Producto");%>
        <form action="consumidor" method="POST">
            <div align="center">
                <table>
                    <tr>
                        <td>Nombre:</td>
                        <td><input id="nombre" type="text" size="20" name="nombre" value="<%= producto.getNombre()%>"></td>
                    </tr>
                    <tr>
                        <td>Precio:</td>
                        <td><input id="precio" type="text" size="20" name="precio" value="<%= producto.getPrecio()%>"></td>
                    </tr>
                    <tr>
                        <td>Comercio:</td>
                        <td><input id="comercio" type="text" size="20" name="comercio" value="<%= producto.getIdcomercio()%>"></td>
                    </tr>
                </table>
            </div>

            <label for="calificacion">Calificar:</label>
            <select id="calificacion" name="calificacion">
                <option value="1"> 1 </option>
                <option value="2"> 2 </option>
                <option value="3"> 3 </option>
                <option value="4"> 4 </option>
                <option value="5"> 5 </option>
            </select>
            <label for="mensaje"> Mensaje:</label>
            <input id="mensaje" type="text">
            <input id="solicitud" name ="solicitud" type="hidden" value="productoCalificado">
            <input type="submit" value="submit">
        </form>
    </body>
</html>
