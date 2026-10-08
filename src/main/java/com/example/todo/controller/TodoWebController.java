package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoWebController {

    private final TodoRepository todoRepository;

    public TodoWebController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/todo2")
    public String index(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        model.addAttribute("newTodo", new Todo());
        return "todoapp";
    }

    @PostMapping( "/todo2")
    public String createTodo(@ModelAttribute("newTodo") Todo todo) {
        if (todo.getTask() != null && !todo.getTask().trim().isEmpty()) {
            todoRepository.save(todo);
        }
        return "redirect:/todo2";
    }
}
