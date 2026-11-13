package ru.boris.naujava.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException exception) {
        String message = exception.getMessage() == null
                ? "Некорректный запрос" : exception.getMessage();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception exception) {
        String message = exception.getMessage() == null
                ? "Внутренняя ошибка сервера" : exception.getMessage();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(message));
    }
}

