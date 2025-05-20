// Define o pacote onde a exceção personalizada está localizada
package com.example.employeemanagement.exception;

// Cria uma exceção personalizada chamada ResourceNotFoundException
// Ela estende RuntimeException, o que significa que é uma exceção não verificada (unchecked)
public class ResourceNotFoundException extends RuntimeException {

    // Construtor da exceção que recebe uma mensagem personalizada
    // A mensagem é passada para o construtor da superclasse (RuntimeException)
    public ResourceNotFoundException(String message) {
        super(message); // Define a mensagem da exceção que poderá ser acessada com getMessage()
    }
}
