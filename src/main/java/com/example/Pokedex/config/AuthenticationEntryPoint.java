package com.example.Pokedex.config;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException{
        response.addHeader("WWW-Authenticate", "Basic realm - " + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        var writer = response.getWriter();
        writer.println("Http Status 401 Bad credentials");
    }
    @Override
    public void afterPropertiesSet(){
        setRealmName("PokeDex Inc");
        super.afterPropertiesSet();
    }
}
