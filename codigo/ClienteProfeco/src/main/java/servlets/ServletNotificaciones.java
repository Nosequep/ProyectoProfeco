/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.profeco.rabitmq.Consumer;
import com.profeco.rabitmq.Sender;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
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
public class ServletNotificaciones  extends HttpServlet{
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        String solicitud = req.getParameter("solicitud");
        if(solicitud.equals("avisar")){
            String mensaje= req.getParameter("mensaje");
            Sender sender = new Sender();
            sender.enviar(mensaje);
            RequestDispatcher dispatcher;
            dispatcher = req.getRequestDispatcher("MenuMercado.jsp");
            try {
                dispatcher.forward(req, res);
            } catch (ServletException ex) {
                Logger.getLogger(ServletNotificaciones.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServletNotificaciones.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse res){
        String solicitud = req.getParameter("solicitud");
        if(solicitud.equals("")){
            
        }
        final List<String> mensajes = new ArrayList<String>();
        Consumer consumer = new Consumer() {
            
            @Override
            public void funcionExtra(String mensaje) {
                mensajes.add(mensaje);
                try {
                    RequestDispatcher dispatcher;
                    req.setAttribute("avisos", mensajes);
                    dispatcher = req.getRequestDispatcher("DesplegarNotificaciones.jsp");
                    dispatcher.forward(req, res);
                } catch (ServletException ex) {
                    Logger.getLogger(ServletNotificaciones.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ServletNotificaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        try {
            consumer.start();
        } catch (IOException ex) {
            Logger.getLogger(ServletNotificaciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(ServletNotificaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RequestDispatcher dispatcher;
        dispatcher = req.getRequestDispatcher("DesplegarNotificaciones.jsp");
        try {
            dispatcher.forward(req, res);
        } catch (ServletException ex) {
            Logger.getLogger(ServletNotificaciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServletNotificaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
