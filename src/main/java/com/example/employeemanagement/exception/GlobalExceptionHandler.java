package com.example.employeemanagement.exception;

// Importa o Logger do SLF4J, que é utilizado para registrar logs na aplicação.
// SLF4J (Simple Logging Facade for Java) é uma biblioteca que fornece uma fachada para diversos frameworks de logging.
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
// Importa a classe HttpStatus do Spring, que define os códigos de status HTTP.
// Essa classe é usada para associar códigos HTTP às respostas da API (ex: 404, 400, 500, etc).
import org.springframework.http.HttpStatus;

// Importa a classe FieldError do Spring, que representa um erro de validação em um campo específico de um objeto.
// Usado principalmente para capturar erros de validação de objetos nos controllers da API.
import org.springframework.validation.FieldError;

// Importa a exceção MethodArgumentNotValidException, que é lançada quando os argumentos de um método não são válidos.
// Isso é comum quando há falha na validação de dados de entrada, como parâmetros passados na requisição HTTP.
import org.springframework.web.bind.MethodArgumentNotValidException;

// Importa as anotações do Spring para manipulação de exceções em controladores.
// @ExceptionHandler é usada para capturar exceções específicas e tratá-las adequadamente.
// @ResponseStatus define o status HTTP que será retornado para o cliente em caso de erro.
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Importa a exceção NoResourceFoundException, que é lançada quando um recurso solicitado não é encontrado.
// Ela é utilizada para capturar erros de recurso não encontrado, especialmente em casos de falha em buscar dados no banco.
import org.springframework.web.servlet.resource.NoResourceFoundException;


// Importa o Map do Java, que é uma estrutura de dados que armazena pares chave-valor.
// Ele é usado para mapear os erros de validação dos campos de entrada em um formato legível.
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that processes exceptions thrown by controllers
 * and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors for method arguments.
     * @param ex the validation exception
     * @return map of field errors with corresponding messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.warn("Erros de validação: {}", errors);
        return errors;
    }

    /**
     * Handles resource not found exceptions.
     * @param ex the not found exception
     * @return error response with the exception message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return response;
    }

    /**
     * Handles illegal argument exceptions.
     * @param ex the illegal argument exception
     * @return error response with the exception message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return response;
    }

    /**
     * Handles email already exists exceptions.
     * @param ex the email conflict exception
     * @return error response with the exception message
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleEmailExists(EmailAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        logger.warn("Conflito de e-mail detectado: {}", ex.getMessage());
        return response;
    }

    /**
     * Handles role name already exists exceptions.
     * @param ex the role name conflict exception
     * @return error response with the exception message
     */
    @ExceptionHandler(RoleNameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleRoleNameExists(RoleNameAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        logger.warn("Conflito de nome de cargo detectado: {}", ex.getMessage());
        return response;
    }

    /**
     * Handles data integrity violation exceptions from the database.
     * @param ex the data integrity violation exception
     * @return generic error response about constraint violation
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        logger.error("Data integrity violation", ex);
        Map<String, String> response = new HashMap<>();
        response.put("error", "Violação de restrição de banco de dados");
        response.put("details", "A operação causaria dados duplicados");
        return response;
    }

    /**
     * Handles cases where a static resource is not found.
     * @param ex the resource not found exception
     * @return error response about missing resource
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoResourceFound(NoResourceFoundException ex) {
        logger.debug("Recurso não encontrado: {}", ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("error", "Recurso não encontrado");
        response.put("details", ex.getMessage());
        return response;
    }

    /**
     * Handles all other unexpected exceptions.
     * @param ex the unexpected exception
     * @return generic error response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGeneralException(Exception ex) {
        logger.error("Ocorreu um erro inesperado", ex);
        Map<String, String> response = new HashMap<>();
        response.put("error", "Ocorreu um erro inesperado");
        response.put("details", ex.getClass().getName() + ": " + ex.getMessage());
        return response;
    }
}