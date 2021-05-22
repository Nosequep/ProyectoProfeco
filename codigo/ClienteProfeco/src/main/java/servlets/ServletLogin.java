/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Lenovo
 */

public class ServletLogin extends HttpServlet{
    /*
        Metodo para solicitudes POST
    */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        
       BufferedReader reader;
       String line;
       StringBuffer responseContent = new StringBuffer();
        try {
            
            PrintWriter out = res.getWriter();
            
            String usuario = req.getParameter("user");
            String contra = req.getParameter("password");
    

            Usuario usuarioObj = new Usuario(0, usuario, contra, "asdf");
            URL url = new URL("http://localhost:9999/profeco/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(usuarioObj);

            //Pasar los datos al url
            System.out.println("Json " + jsonInputString);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes();
                os.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            int status = con.getResponseCode();
            //Respuesta de la solicitud
            if(status > 299){
                //Cuando un error
                //reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
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
            
            status = con.getResponseCode();
            if(status > 299){
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Regrese al login e ingrese una credencial valida</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1> Credenciales invalidas</h1>");
                out.println("<a href=\"index.html#\">LOGIN</a>");
                out.println("</body>");
                out.println("</html>");
            }else{
                if (usuario.equals("mercado")) {
                    res.sendRedirect("MenuMercado.html");
                }else if(usuario.equals("profeco")){
                    res.sendRedirect("MenuProfeco.html");
                }else{
                    res.sendRedirect("MenuConsumidor.html");
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
