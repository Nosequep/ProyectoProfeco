/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.profeco.entidades.Comercio;
import com.profeco.entidades.Sancion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class ServletProfeco extends HttpServlet{
    /*
        Metodo para solicitudes POST
    */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException{
         BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        RequestDispatcher dispatcher;
        try {
          
            PrintWriter out = res.getWriter();
    
            String solicitud = req.getParameter("solicitud");
            
            String comercio = req.getParameter("comercio");
            String asunto = req.getParameter("asunto");
            String descripcion = req.getParameter("descripcion");
            String castigos = req.getParameter("castigos");
            String multa = req.getParameter("multa");
   
            System.out.println("Solicitud " + solicitud);
            URL url = null;
            HttpURLConnection con = null;
            String jsonInputString = null;
            if(solicitud.equals("subirSancion")){
                
                Sancion sancion = new Sancion();
                sancion.setCabecera(asunto);
                sancion.setIdcomercio(new Comercio(Integer.parseInt(comercio)));
                sancion.setDescripcion(descripcion);
                sancion.setCastigos(castigos);
                sancion.setMulta(Float.parseFloat(multa));
                

                url = new URL("http://localhost:9999/profeco/subirSancion");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "token");
                con.setDoOutput(true);
                con.setDoInput(true);
                Gson gson = new Gson();
                jsonInputString = gson.toJson(sancion);
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
                dispatcher = req.getRequestDispatcher("MenuProfeco.jsp");
                dispatcher.forward(req, res);

            }
        } catch (IOException ex) {
            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException{
        
    }

}
