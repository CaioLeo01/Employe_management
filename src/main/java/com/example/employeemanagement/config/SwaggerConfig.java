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

@Configuration // Indica que esta classe é uma classe de configuração do Spring
public class SwaggerConfig {

    // Cria e retorna uma instância de TypeResolver que será gerenciada pelo Spring
    @Bean
    public TypeResolver typeResolver() {
        return new TypeResolver();
    }

    // Configuração principal do Swagger/OpenAPI
    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2) // Configura para usar a versão 2 do Swagger
                .select()
                // Define o pacote base onde o Swagger deve procurar por controllers
                .apis(RequestHandlerSelectors.basePackage("com.example.employeemanagement.controller"))
                // Define os paths que devem ser incluídos na documentação (endpoints de /cargos e /empregados)
                .paths(PathSelectors.ant("/cargos/**").or(PathSelectors.ant("/empregados/**")))
                .build()
                // Adiciona informações sobre a API
                .apiInfo(apiInfo())
                // Substitui o tipo Page (do Spring Data) por Object na documentação para evitar problemas
                .directModelSubstitute(Page.class, Object.class);
    }

    // Método privado que cria e retorna as informações básicas da API
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Gestão de Funcionários API") // Título da API
                .description("REST API for Employee and Role Management") // Descrição da API
                .version("0.0.1-SNAPSHOT") // Versão da API
                .build();
    }
}