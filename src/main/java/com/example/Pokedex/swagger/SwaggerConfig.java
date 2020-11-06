package com.example.Pokedex.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
        <description>
        @author Marcus Sandberg
        @since 2020-11-05
*/

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customSwaggerConfig(){
        final var pack = "com.example.Pokedex.controllers";
        final var host = "localhost:8080";
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build().apiInfo(this.getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("Pokédex API Docs")
                .description("This is an assignment/Examination for Java Spring @Teknikhögskolan Lund")
                .contact(new Contact(
                        "Marcus Sandberg",
                        "https://github.com/SandbergM/",
                        "marcus.sandberg@outlook.com"))
                .build();
    }
}
