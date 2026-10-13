package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Same endpoints as TodoController, backed by PlainJdbcTodoRepository instead
 * of TodoRepository, so you can curl both and see they behave identically -
 * the only difference is what's happening inside the repository.
 */
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }


    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id ) {
        todoRepository.delete(id);
    }

    @PutMapping
    public void update(@RequestBody Todo todo ) {
        todoRepository.update(todo);
    }
}