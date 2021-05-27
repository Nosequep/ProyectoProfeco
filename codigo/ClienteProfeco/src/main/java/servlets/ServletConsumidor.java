/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profeco.controladores.CalificacionJpaController;
import com.profeco.controladores.ComercioJpaController;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.profeco.controladores.ProductoJpaController;
import com.profeco.controladores.exceptions.NonexistentEntityException;
import com.profeco.entidades.Calificacion;
import com.profeco.entidades.Comercio;
import com.profeco.entidades.Producto;
import com.profeco.entidades.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.ws.rs.HEAD;

/**
 *
 * @author Lenovo
 */
public class ServletConsumidor extends HttpServlet {

    ProductoJpaController consultas = new ProductoJpaController();
    ComercioJpaController consultarComercios = new ComercioJpaController();
    CalificacionJpaController consultaCalificaciones = new CalificacionJpaController();


    /*
        Metodo para solicitudes POST
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException {

        RequestDispatcher dispatcher;

        String solicitud = req.getParameter("solicitud");
        String nombre = req.getParameter("nombre");
        String id = req.getParameter("idProducto");
        String precio = req.getParameter("precio");
        String oferta = req.getParameter("oferta");
        String idcomercio = req.getParameter("comercio");

        if (solicitud.equalsIgnoreCase("calificarProducto")) {
            Producto temp1 = new Producto();
            temp1.setNombre(nombre);
            temp1.setIdproducto(Integer.parseInt(id));
            temp1.setPrecio(Double.parseDouble(precio));
            temp1.setOferta(Double.parseDouble(oferta));
            temp1.setIdcomercio(new Comercio(Integer.parseInt(idcomercio)));
            try {
                req.setAttribute("Producto", temp1);
                req.getRequestDispatcher("CalificarProducto.jsp").forward(req, res);
            } catch (IOException ex) {
                Logger.getLogger(ServletConsumidor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (solicitud.equalsIgnoreCase("productoCalificado")) {

            Producto temp1 = new Producto();
            temp1.setNombre(nombre);
            temp1.setPrecio(Double.parseDouble(precio));
            temp1.setIdcomercio(new Comercio(Integer.parseInt(idcomercio)));

            String calificacion = req.getParameter("calificacion");
            String mensaje = req.getParameter("mensaje");
            try {
                Calificacion cali = new Calificacion();
                cali.setCalificacion(Integer.parseInt(calificacion));
                cali.setComentario(mensaje);
                cali.setIdUsuario(new Usuario(3));
                cali.setIdComercio(new Comercio(Integer.parseInt(idcomercio)));
                consultaCalificaciones.create(cali);
//                System.out.println(consultaCalificaciones.findCalificacion(Integer.SIZE));
            } catch (Exception e) {
                Logger.getLogger(ServletConsumidor.class.getName()).log(Level.SEVERE, null, e);
            }
            Comercio comercio = consultarComercios.findComercio(temp1.getIdcomercio().getIdcomercio());
            comercio.setCalificacion(Integer.parseInt(calificacion));
            try {
                consultarComercios.edit(comercio);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ServletConsumidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ServletConsumidor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        dispatcher = req.getRequestDispatcher("MenuConsumidor.jsp");
        try {
            dispatcher.forward(req, res);
        } catch (IOException ex) {
            Logger.getLogger(ServletConsumidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        String solicitud = req.getParameter("solicitud");
        if (solicitud.equals("desplegarOfertas")) {

            BufferedReader reader;
            String line;
            StringBuffer responseContent = new StringBuffer();
            RequestDispatcher dispatcher;
            try {

                PrintWriter out = res.getWriter();
                URL url = null;
                HttpURLConnection con = null;
                String jsonInputString = null;

                url = new URL("http://localhost:9999/profeco/desplegarOfertas");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "token");
                con.setDoOutput(true);
                con.setDoInput(true);

                int status = con.getResponseCode();
                System.out.println("codigo " + status);
                //Respuesta de la solicitud
                if (status > 299) {
                    //Cuando un error
                    //reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    dispatcher = req.getRequestDispatcher("paginaError.jsp");
                    dispatcher.forward(req, res);
                } else {
                    //Cuando la solicitud es exitosa
                    //Leer lo obtenido de la respuesta
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    reader.close();
                    System.out.println(responseContent.toString());
                    String respuesta = responseContent.toString();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Producto>>() {
                    }.getType();
                    List<Producto> ofertas = gson.fromJson(respuesta, listType);
                    req.setAttribute("ofertas", ofertas);
                    dispatcher = req.getRequestDispatcher("DesplegarOfertas.jsp");
                    dispatcher.forward(req, res);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (solicitud.equals("buscarProducto")) {
            System.out.println("Solicitud " + solicitud);
            Producto temp1;
            String nombre = req.getParameter("nombre");
            List<Producto> productos = new ArrayList<>();
            List<Producto> temp = consultas.findProductoEntities();
            for (Producto producto : temp) {
                if (producto.getNombre().equalsIgnoreCase(nombre)) {
                    temp1 = producto;
                    productos.add(temp1);
                }
            }

            if (!productos.isEmpty()) {
                for (Producto producto : productos) {
                    req.setAttribute("Producto", producto);
                }
                try {
                    req.getRequestDispatcher("MostrarProducto.jsp").forward(req, res);
                } catch (IOException ex) {
                    Logger.getLogger(ServletConsumidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    req.getRequestDispatcher("paginaError.jsp").forward(req, res);
                } catch (IOException ex) {
                    Logger.getLogger(ServletConsumidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
