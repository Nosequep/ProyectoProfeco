<%-- 
    Document   : MostrarProducto
    Created on : May 25, 2021, 12:45:07 PM
    Author     : joelvalenzuela
--%>

<%@page import="com.profeco.entidades.Producto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulta Producto</title>
</head>
<body>
<%Producto producto=(Producto)request.getAttribute("Producto"); %>
<div align="center">
<table>
<tr>
<td>Nombre:</td>
<td><input id="nombre" type="text" size="20" name="nombre" value="<%= producto.getNombre() %>"></td>
</tr>
<tr>
<td>ID:</td>
<td><input id="idProducto" type="text" size="20" name="id" value="<%= producto.getIdproducto() %>"></td>
</tr>
<tr>
<td>Precio:</td>
<td><input id="precio" type="text" size="20" name="precio" value="<%= producto.getPrecio() %>"></td>
</tr>
<tr>
<td>Oferta:</td>
<td><input id="oferta" type="text" size="20" name="oferta" value="<%= producto.getOferta() %>"></td>
</tr>
<tr>
<td>Comercio:</td>
<td><input id="comercio" type="text" size="20" name="comercio" value="<%= producto.getIdcomercio() %>"></td>
</tr>
</table>
</div>
<form action="consumidor" method="POST">
            <input id="solicitud" name ="solicitud" type="hidden" value="calificarProducto">
            <input type="submit" value="submit">
        </form>
</body>
</html>
