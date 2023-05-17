package com.tothandras.talkabotexam.todo.services;

import com.tothandras.talkabotexam.todo.model.Todo;
import com.tothandras.talkabotexam.todo.model.request.TodoListRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SortingService {

    List<Todo> processRequest(TodoListRequest todoListRequest);
}
