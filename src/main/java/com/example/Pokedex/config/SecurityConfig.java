package com.example.Pokedex.config;


import com.example.Pokedex.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-10-25
*/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint entryPoint;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .authorizeRequests()
                // GET("/") is left on permitAll() for presentational purpose, would be
                // .authenticated() otherwise
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/pokemon").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/pokemon**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/item").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/item**").permitAll()
                .antMatchers("/api**").authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(entryPoint)
                .and()
                .logout(l -> l.logoutSuccessUrl("/"));
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
