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
        <main>
            <h2 id="tituloFuncion">Lista de productos con ofertas</h2>
            <div class="formularioTabla">
                <form action="MenuConsumidor.jsp">
                    <table>
                        <tr>
                            <th>idproducto</th>
                            <th>nombre</th>
                            <th>precio</th>
                            <th>oferta</th>
                        </tr>
                        <c:forEach items="${ofertas}" var="oferta" varStatus="estado">
                            <tr>
                                <td>${oferta.idproducto}</td>
                                <td>${oferta.nombre}</td>
                                <td>${oferta.precio}</td>
                                <td>${oferta.oferta}</td>
                            </tr>
                        </c:forEach>
                    </table>   
                    <input type="submit" value="Regresar a menu">
                </form>
            </div>
        </main>
        
          
    </body>
</html>

