/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, MalformedURLException, IOException {

        StringBuffer responseContent = new StringBuffer();
        BufferedReader reader;
        String line;

        String stringURL = "http://localhost:9999/profeco/ofertas";
        URL url = new URL(stringURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        
        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }
        System.out.println(responseContent);
        reader.close();

        System.out.println("HOla");
        

    }
}
