/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitas.clienteprofecto;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 *
 * @author Lenovo
 */
public class Cliente {
    
    
    private HttpURLConnection connection;
    
    public Cliente(){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL url = new URL("http://localhost:8080/products");
            this.connection = (HttpURLConnection)url.openConnection();
            
            //Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int status = connection.getResponseCode();
            System.out.println(status);
            
            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                
            }else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            
            while((line = reader.readLine()) != null){
                responseContent.append(line);
            }
            reader.close();
            String respuesta = responseContent.toString();
            Gson gson = new Gson();
            Type type = new TypeToken<List<Producto>>() {}.getType();
            gson.fromJson(respuesta, type);
        } catch (MalformedURLException ex ) {
            ex.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        Cliente cliente = new Cliente();
    }
}
