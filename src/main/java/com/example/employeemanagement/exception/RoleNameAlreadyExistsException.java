package com.example.employeemanagement.exception;

/**
 * Exceção personalizada lançada ao tentar criar ou atualizar um cargo
 * com um nome que já existe no sistema.
 */
public class RoleNameAlreadyExistsException extends RuntimeException {
    /**
     * Constrói uma nova exceção com a mensagem detalhada especificada.
     * @param message a mensagem detalhada explicando o conflito de nome do cargo
     */
    public RoleNameAlreadyExistsException(String message) {
        super(message);
    }
}
