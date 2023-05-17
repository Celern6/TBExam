package com.tothandras.talkabotexam.todo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "todo")
@Table(name = "todo", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Todo {

    @Id
    @SequenceGenerator(
            name="todo_seq",
            sequenceName = "todo_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "dateofcreation")
    private LocalDateTime dateOfCreation;

    @Column(name = "priority")
    private Integer priority;

    public Todo(TodoDTO todoDTO) {
        this.name = todoDTO.getName();
        this.deadline = todoDTO.getDeadline();
        this.dateOfCreation = LocalDateTime.now();
        this.priority = todoDTO.getPriority().getValue();
    }
}
