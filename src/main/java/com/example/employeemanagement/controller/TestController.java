package com.example.employeemanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indica que esta classe é um controller REST que retorna diretamente dados (não views)
public class TestController {
    
    // Endpoint simples para teste
    @GetMapping("/test") // Mapeia requisições GET para o caminho "/test"
    public String test() {
        return "Test endpoint"; // Retorna uma string simples como resposta
    }
}