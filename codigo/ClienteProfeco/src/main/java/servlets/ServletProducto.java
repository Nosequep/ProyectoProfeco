/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */

public class ServletProducto extends HttpServlet{
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        
       BufferedReader reader;
       String line;
       StringBuffer responseContent = new StringBuffer();
        try {
            PrintWriter out = res.getWriter();
            URL url = new URL("http://localhost:9999/profeco/consumidor/chetis");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            
            int status = con.getResponseCode();
            //Respuesta de la solicitud
            if(status > 299){
                //Cuando un error
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }else{
                //Cuando la solicitud es exitosa
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
           
            //Leer lo obtenido de la respuesta
            while((line = reader.readLine()) != null){
                responseContent.append(line);
            }
            reader.close();
            System.out.println(responseContent.toString());
            
        } catch (IOException ex) {
            Logger.getLogger(ServletProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        
    }
}
