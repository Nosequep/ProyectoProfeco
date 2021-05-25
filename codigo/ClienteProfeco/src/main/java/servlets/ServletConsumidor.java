/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.profeco.controladores.ProductoJpaController;
import com.profeco.entidades.Producto;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class ServletConsumidor extends HttpServlet {
    ProductoJpaController consultas = new ProductoJpaController();
    /*
        Metodo para solicitudes POST
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException {
           
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        String solicitud = req.getParameter("solicitud");
        System.out.println("Solicitud " + solicitud);
        Producto temp1;
        List<Producto> productos = new ArrayList<>();
        List<Producto> temp = consultas.findProductoEntities();
        for (Producto producto : temp) {
            if(producto.getNombre().equalsIgnoreCase(solicitud)){
                temp1 = producto;
                productos.add(temp1);
            }
        }
        
        if(!productos.isEmpty()){
            for (Producto producto : productos) {
                req.setAttribute("Producto", producto.getNombre());
            }
            req.getRequestDispatcher("MostrarProducto.jsp").forward(req, res);
        }else{
            PrintWriter out=res.getWriter();
            out.println("Error, no se encontro el Producto.");
        }
    }
}
