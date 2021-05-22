
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.profeco.filter;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;




import org.springframework.core.annotation.Order;
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
        System.out.println("Entra en el filtro");
        chain.doFilter(request, response);
       
        
    }

}
