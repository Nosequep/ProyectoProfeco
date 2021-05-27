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
            <h2 id="tituloFuncion">Notificaciones</h2>
            <div class="formularioTabla">
                <form action="MenuConsumidor.jsp" >
                    <table>
                        <tr>
                            <th>Mensajes</th>
                        </tr>
                        <c:forEach items="${avisos}" var="aviso" varStatus="estado">
                            <tr>
                                <td>${aviso}</td>
                            </tr>
                        </c:forEach>
                    </table> 
                    <input type="submit" value="Regresar a menu">
                </form>
            </div>
        </main>
        
          
    </body>
</html>
