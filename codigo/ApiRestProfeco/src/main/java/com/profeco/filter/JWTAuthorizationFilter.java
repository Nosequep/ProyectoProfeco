
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profeco.filter;

import java.io.IOException;
import javafx.animation.Animation.Status;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lenovo
 */
@Component
//@Order(1)
public class JWTAuthorizationFilter implements Filter{
    private final String AUTHENTICATION_HEADER = "Authorization";
    private final String PREFIX = "Token ";
    private final String SECRET_KEY = "miLlave";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String method = req.getMethod();
        String path = req.getRequestURI();
        System.out.println("Method: " + method);
        System.out.println("Path: " + path);
        
        if(method.equals("POST") && path.contains("login")){
            System.out.println("Entrando al filtro");
            chain.doFilter(request, response);
        }else{
            String token = req.getHeader(AUTHENTICATION_HEADER);
            System.out.println("Token: " +  token);
            if(token != null){
                System.out.println("Verificar token");
            }else{
                //throw new WebApplicationException(Status.UNAUTHORIZED);
            }
        }
        
        
    }

}
