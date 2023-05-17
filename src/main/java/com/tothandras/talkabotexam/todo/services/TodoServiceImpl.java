package com.tothandras.talkabotexam.todo.services;

import com.tothandras.talkabotexam.todo.exceptions.ItemNotFoundException;
import com.tothandras.talkabotexam.todo.model.Todo;
import com.tothandras.talkabotexam.todo.model.TodoDTO;
import com.tothandras.talkabotexam.todo.model.request.TodoListRequest;
import com.tothandras.talkabotexam.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;

    private final SortingService sortingService;

    public TodoServiceImpl(TodoRepository todoRepository, SortingService sortingService) {
        this.repository = todoRepository;
        this.sortingService = sortingService;
    }

    @Override
    public List<TodoDTO> getTodoItems(TodoListRequest request) {
        if (request == null || request.getSorting() == null && request.getFilters() == null && request.getDateFilters() == null) {
            return getUnfilteredTodoList();
        }

        return  sortingService.processRequest(request)
                .stream()
                .map(TodoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TodoDTO createTodoItems(TodoDTO todoDTO) {
        return Optional.of(todoDTO)
                .map(Todo::new)
                .map(repository::save)
                .map(TodoDTO::new)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public TodoDTO editTodoItem(TodoDTO todoDTO) {
        return repository.findById(todoDTO.getId())
                .map(todo -> {
                    todo.setName(todoDTO.getName());
                    todo.setDeadline(todoDTO.getDeadline());
                    todo.setPriority(todoDTO.getPriority().getValue());
                    return repository.save(todo);
                })
                .map(TodoDTO::new)
                .orElseThrow(ItemNotFoundException::new);

    }

    @Override
    public void deleteTodoItem(Long id) {
        Optional.of(id)
                .ifPresent(repository::deleteById);
    }

    private List<TodoDTO> getUnfilteredTodoList() {
        return repository.findAll().stream()
                .map(TodoDTO::new)
                .collect(Collectors.toList());
    }


}
