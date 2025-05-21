package com.example.employeemanagement.exception;

/**
 * Exceção lançada quando uma tentativa de cadastrar ou atualizar um empregado
 * com um e-mail que já está registrado no sistema.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    /**
     * Cria uma nova instância da exceção com uma mensagem de erro específica.
     * @param message Mensagem detalhando o conflito de e-mail
     */
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}