package com.tothandras.talkabotexam.todo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    
    private Long id;

    @NotNull(message = "Name should not be null.")
    private String name;

    @DateTimeFormat(pattern = "YYYY-MM-DDTHH:mm:ss")
    private LocalDateTime deadline;

    @DateTimeFormat(pattern = "YYYY-MM-DDTHH:mm:ss")
    private LocalDateTime dateOfCreation;

    @NotNull(message = "You must specify a priority.")
    private Priority priority;

    public TodoDTO(Todo todo) {
        this.id = todo.getId();
        this.name = todo.getName();
        this.deadline = todo.getDeadline();
        this.dateOfCreation = todo.getDateOfCreation();
        this.priority = Priority.valueOfLabel(todo.getPriority());
    }
}
