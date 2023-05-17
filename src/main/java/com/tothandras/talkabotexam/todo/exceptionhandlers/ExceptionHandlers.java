package com.tothandras.talkabotexam.todo.exceptionhandlers;

import com.tothandras.talkabotexam.todo.exceptions.InvalidPriorityException;
import com.tothandras.talkabotexam.todo.exceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Date;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMessage = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(e -> {
            errorMessage.put(e.getField(), e.getDefaultMessage());
        });
        return errorMessage;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public Map<String, String> handleInvalidDateTimeFormat(DateTimeParseException exception) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put(exception.getParsedString(), "Please add a valid DateTime format, like: YYYY-MM-DDTHH:mm:ss");
        return errorMessage;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPriorityException.class)
    public String handleInvalidPriority(InvalidPriorityException exception) {
        return "Please add a valid priority: HIGH, MEDIUM or LOW";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemNotFoundException.class)
    public String handleInvalidItem(ItemNotFoundException exception) {
        return "The specified item does not exist.";
    }



}
