package com.example.Pokedex.swagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-06
*/

@SpringBootApplication
class SwaggerWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerWebappApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        com.example.Pokedex.swagger.MyFilter myFilter = new MyFilter();
        filterRegistrationBean.setFilter(myFilter);
        return filterRegistrationBean;
    }
}