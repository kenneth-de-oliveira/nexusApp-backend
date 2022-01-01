package br.com.nexusapp.api.exception.handler;

import br.com.nexusapp.api.exception.ConflictException;
import br.com.nexusapp.api.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class NexusAppBackendExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public NexusAppBackendExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Error> handleConflictException(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Error(
        LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(
        LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
        ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Error> handleConstraintViolationException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(
        LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
        ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new Error(
        LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
        messageSource.getMessage("mensagem.invalida",
    null, LocaleContextHolder.getLocale())), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, criarListaDeErros(ex.getBindingResult()),
        headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> criarListaDeErros(BindingResult bindingResult) {
        List<Error> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(
        fieldError -> errors.add(new Error(LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        messageSource.getMessage(fieldError,
        LocaleContextHolder.getLocale()))));
        return errors;
    }

    static class Error {

        private LocalDateTime timestamp;

        private Integer status;

        private String message;

        public Integer getStatus() {
            return status;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Error(String message) {
            this.message = message;
        }

        public Error(LocalDateTime timestamp, Integer status, String message) {
            this.timestamp = timestamp;
            this.status = status;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
