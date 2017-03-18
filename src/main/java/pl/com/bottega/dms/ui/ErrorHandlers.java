package pl.com.bottega.dms.ui;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.com.bottega.dms.application.user.AuthRequiredException;
import pl.com.bottega.dms.model.DocumentNotFoundException;
import pl.com.bottega.dms.model.DocumentStatusException;
import pl.com.bottega.dms.model.commands.CommandInvalidException;
import pl.com.bottega.dms.model.commands.Validatable;

@ControllerAdvice
public class ErrorHandlers {

    @ExceptionHandler(AuthRequiredException.class)
    public ResponseEntity<String> handleAuthRequiredException() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<String>(
                "{\"error\": \"authentication_required\"}",
                headers,
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(DocumentStatusException.class)
    public ResponseEntity<String> handleDocumentStatusException(DocumentStatusException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<String>(
                String.format("{\"error\": \"document_status_error\", \"details\": \"%s\"}", ex.getMessage()),
                headers,
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(CommandInvalidException.class)
    public ResponseEntity<Validatable.ValidationErrors> handleCommandInvalidExeption(CommandInvalidException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<Validatable.ValidationErrors>(
                ex.getErrors(),
                headers,
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<String> handleDocumentNotFoundException(DocumentNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<String>(
                String.format("{\"error\": \"%s\"}", ex.getMessage()),
                headers,
                HttpStatus.NOT_FOUND
        );
    }

}
