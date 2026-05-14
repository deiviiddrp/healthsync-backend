package com.healthsync.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex, WebRequest request) {
        return buildError(HttpStatus.NOT_FOUND,
            ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex, WebRequest request) {
        return buildError(HttpStatus.BAD_REQUEST,
            ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining("; "));
        return buildError(HttpStatus.BAD_REQUEST,
            errors, request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex, WebRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,
            "Error interno del servidor", request.getDescription(false));
    }

    private ResponseEntity<ErrorResponse> buildError(
            HttpStatus status, String message, String path) {
        ErrorResponse body = new ErrorResponse(
            LocalDateTime.now(), status.value(),
            status.getReasonPhrase(), message, path);
        return ResponseEntity.status(status).body(body);
    }

    public record ErrorResponse(LocalDateTime timestamp, int status,
        String error, String message, String path) {}
}