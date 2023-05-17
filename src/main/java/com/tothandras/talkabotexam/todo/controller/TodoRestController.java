package com.tothandras.talkabotexam.todo.controller;

import com.tothandras.talkabotexam.todo.model.TodoDTO;
import com.tothandras.talkabotexam.todo.model.request.TodoListRequest;
import com.tothandras.talkabotexam.todo.services.TodoService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TodoRestController {

    private final TodoService todoService;

    public TodoRestController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<TodoDTO>> getTodoItems(@RequestBody @Nullable TodoListRequest request) {
        return new ResponseEntity<>(todoService.getTodoItems(request), HttpStatus.OK);
    };

    @PostMapping("/create")
    public ResponseEntity<TodoDTO> createTodoItem(@Valid @RequestBody TodoDTO todoDTO) {
        return new ResponseEntity<>(todoService.createTodoItems(todoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<TodoDTO> editTodoItem(@RequestBody TodoDTO todoDTO) {
        return new ResponseEntity<>(todoService.editTodoItem(todoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTodoItem(@PathVariable("id") Long id) {
        todoService.deleteTodoItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
