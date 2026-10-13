package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.model.User;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Same endpoints as TodoController, backed by PlainJdbcTodoRepository instead
 * of TodoRepository, so you can curl both and see they behave identically -
 * the only difference is what's happening inside the repository.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @PostMapping
    public User create(@RequestBody User user) {
       return  userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id ) {
        userRepository.delete(id);
    }

    @PutMapping
    public void update(@RequestBody User user ) {
        userRepository.update(user);
    }
}