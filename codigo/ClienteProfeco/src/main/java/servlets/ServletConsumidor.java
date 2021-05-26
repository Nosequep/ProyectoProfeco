/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profeco.entidades.Producto;
import com.profeco.entidades.Sancion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
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
public class ServletConsumidor extends HttpServlet {

    /*
        Metodo para solicitudes POST
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException {

    }

    @Override
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
    }
}
