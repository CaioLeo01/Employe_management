// Define o pacote base da aplicação
package com.example.employeemanagement;

// Importa a classe responsável por inicializar a aplicação Spring Boot
import org.springframework.boot.SpringApplication;

// Importa a anotação que marca a classe principal da aplicação Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marca esta classe como a principal da aplicação Spring Boot
// Essa anotação combina três outras:
// - @Configuration: Define uma classe de configuração
// - @EnableAutoConfiguration: Ativa a configuração automática do Spring
// - @ComponentScan: Habilita a varredura de componentes no pacote base
@SpringBootApplication
public class GestaoDeFuncionariosApplication {

    // Método principal que inicia a aplicação Spring Boot
    public static void main(String[] args) {
        // Executa a aplicação Spring, inicializando o contexto, web server, beans, etc.
        SpringApplication.run(GestaoDeFuncionariosApplication.class, args);
    }
}
