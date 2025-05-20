// Define o pacote do teste, geralmente o mesmo da aplicação principal para facilitar o acesso aos componentes
package com.example.employeemanagement;

// Importa a anotação para definir o método de teste
import org.junit.jupiter.api.Test;

// Indica que este é um teste de integração que carrega o contexto completo da aplicação Spring Boot
import org.springframework.boot.test.context.SpringBootTest;

// Marca esta classe como um teste de integração que carrega o ApplicationContext do Spring Boot
@SpringBootTest
class GestaoDeFuncionariosApplicationTests {

    // Teste padrão gerado automaticamente pelo Spring Initializr.
    // Serve para garantir que o contexto da aplicação Spring é carregado corretamente.
    @Test
    void contextLoads() {
        // Se o contexto da aplicação falhar ao iniciar, este teste irá falhar.
        // Caso contrário, será considerado bem-sucedido mesmo sem nenhuma asserção.
    }
}
