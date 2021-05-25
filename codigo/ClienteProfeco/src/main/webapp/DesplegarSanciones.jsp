<%-- 
    Document   : despliegaInventarioMateriales
    Created on : Oct 26, 2020, 6:55:52 PM
    Author     : abrah
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comercio: Consultar sanciones</title>
        <link rel="stylesheet" href="css/estilo.css"/>
        <link rel="stylesheet" href="css/formularios.css"/>
    </head>
    <body>
        <main>
            <h2 id="tituloFuncion">Lista de sanciones</h2>
            <div class="formularioTabla">
                <form>
                    <table>
                        <tr>
                            <th>idSancion</th>
                            <th>Asunto</th>
                            <th>Descripción</th>
                            <th>Castigos</th>
                            <th>Multa</th>
                        </tr>
                        <c:forEach items="${sanciones}" var="sancion" varStatus="estado">
                            <tr>
                                <td>${sancion.idsancion}</td>
                                <td>${sancion.cabecera}</td>
                                <td>${sancion.descripcion}</td>
                                <td>${sancion.castigos}</td>
                                <td>${sancion.multa}</td>
                            </tr>
                        </c:forEach>
                    </table>   
                </form>
            </div>
        </main>
        
          
    </body>
</html>
