package com.tothandras.talkabotexam.todo.services;

import com.tothandras.talkabotexam.todo.model.TodoDTO;
import com.tothandras.talkabotexam.todo.model.request.TodoListRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoService {
    public List<TodoDTO> getTodoItems(TodoListRequest request);

    public TodoDTO createTodoItems(TodoDTO todoDTO);

    public TodoDTO editTodoItem(TodoDTO todoDTO);

    public void deleteTodoItem(Long id);
}
