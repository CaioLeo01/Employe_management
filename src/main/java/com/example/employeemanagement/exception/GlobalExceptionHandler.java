// Define o pacote onde esta classe está localizada
package com.example.employeemanagement.exception;

// Importa o logger do SLF4J para registrar mensagens de log
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Importa o status HTTP que será usado nas respostas
import org.springframework.http.HttpStatus;

// Importa a exceção lançada quando uma validação de argumento falha
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

// Anotações para indicar que a classe trata exceções globalmente
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Exceção lançada quando um recurso não é encontrado (por exemplo, uma URL inexistente)
import org.springframework.web.servlet.resource.NoResourceFoundException;

// Importa as estruturas de dados para armazenar mensagens de erro
import java.util.HashMap;
import java.util.Map;

// Anotação que transforma a classe em um manipulador global de exceções para controladores REST
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Logger para registrar mensagens de log (aviso, erro, debug)
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Trata exceções de validação (por exemplo, campos inválidos em DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Retorna status 400
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Para cada erro de validação, extrai o nome do campo e a mensagem
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Loga os erros de validação com nível "warn"
        logger.warn("Validation errors: {}", errors);
        return errors; // Retorna um mapa com os erros de validação
    }

    // Trata exceções customizadas quando um recurso não é encontrado na aplicação
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Retorna status 404
    public Map<String, String> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage()); // Retorna a mensagem da exceção
        return response;
    }

    // Trata exceções de argumento inválido (IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Retorna status 400
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage()); // Retorna a mensagem da exceção
        return response;
    }

    // Trata o caso em que um recurso (como uma rota/endpoint) não é encontrado
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Retorna status 404
    public Map<String, String> handleNoResourceFound(NoResourceFoundException ex) {
        // Loga a mensagem no nível "debug"
        logger.debug("Resource not found: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Resource not found"); // Mensagem padrão
        response.put("details", ex.getMessage());    // Detalhes técnicos
        return response;
    }

    // Trata qualquer outra exceção que não foi capturada pelos manipuladores anteriores
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Retorna status 500
    public Map<String, String> handleGeneralException(Exception ex) {
        // Loga o erro inesperado com nível "error" e inclui a stack trace
        logger.error("Unexpected error occurred", ex);

        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred"); // Mensagem genérica
        response.put("details", ex.getClass().getName() + ": " + ex.getMessage()); // Classe da exceção + mensagem
        return response;
    }
}
