package com.example.Pokedex.swagger;
/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-06
*/

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFilter implements Filter {

    String DOCUMENTATION_URL = "/swagger-ui.html";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getRequestURI().equals("") || request.getRequestURI().equals("/")){
            response.sendRedirect(DOCUMENTATION_URL);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}