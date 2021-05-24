/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.profeco.entidades.Comercio;
import com.profeco.entidades.Producto;
import com.profeco.controladores.ProductoJpaController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */

public class ServletMercado extends HttpServlet{
    /*
        Metodo para solicitudes POST
    */
    ComercioJpaController consultas = new ComercioJpaController();
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException{
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        RequestDispatcher dispatcher;
        try {
          
            PrintWriter out = res.getWriter();
    
            String nombre = req.getParameter("nombre");
            String comercio = req.getParameter("comercio");
            String precio = req.getParameter("precio");
            String oferta = req.getParameter("oferta");
            String solicitud = req.getParameter("solicitud");
            System.out.println("Solicitud " + solicitud);
            URL url = null;
            HttpURLConnection con = null;
            String jsonInputString = null;
            if(solicitud.equals("subirProducto")){
                
                Producto producto = new Producto();
                producto.setNombre(nombre);
                producto.setPrecio(Double.parseDouble(precio));
                producto.setOferta(Double.parseDouble(oferta));
                producto.setIdcomercio(new Comercio(Integer.parseInt(comercio)));

                url = new URL("http://localhost:9999/profeco/subirProducto");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "token");
                con.setDoOutput(true);
                con.setDoInput(true);
                Gson gson = new Gson();
                jsonInputString = gson.toJson(producto);
            }
            if(solicitud.equals("eliminarProducto")){
                Producto temp = new Producto();
                temp.setNombre(nombre);
                temp.setIdComercio(new Comercio(Integer.parseInt(comercio)));
                ArrayList<Producto> productos = consultas.findProductoEntities();
                
                for (Producto producto : productos) {
                    if(producto.getNombre().equalsIgnoreCase(temp.getNombre()) && producto.getIdComercio() == temp.getIdComercio()){
                        consultas.destroy(producto.getIdProducto());
                        productos.remove(producto);
                        return;
                    }
                }
                url = new URL("http://localhost:9999/profeco/eliminarProducto");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "token");
                con.setDoOutput(true);
                con.setDoInput(true);
                Gson gson = new Gson();
                jsonInputString = gson.toJson(temp);
            }
            //Pasar los datos al url
            System.out.println("Json " + jsonInputString);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes();
                os.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            int status = con.getResponseCode();
            System.out.println("codigo " + status);
            //Respuesta de la solicitud
            if(status > 299){
                //Cuando un error
                //reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                dispatcher = req.getRequestDispatcher("paginaError.jsp");
            }else{
                //Cuando la solicitud es exitosa
                //Leer lo obtenido de la respuesta
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
                 System.out.println(responseContent.toString());

            }
        } catch (IOException ex) {
            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException{
        
    }

}
