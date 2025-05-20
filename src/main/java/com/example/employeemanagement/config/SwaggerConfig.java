package com.example.employeemanagement.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public TypeResolver typeResolver() {
        return new TypeResolver();
    }

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.employeemanagement.controller"))
                .paths(PathSelectors.ant("/cargos/**").or(PathSelectors.ant("/empregados/**")))
                .build()
                .apiInfo(apiInfo())
                .directModelSubstitute(Page.class, Object.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Gestão de Funcionários API")
                .description("REST API for Employee and Role Management")
                .version("0.0.1-SNAPSHOT")
                .build();
    }
}