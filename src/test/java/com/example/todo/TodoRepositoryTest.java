package com.example.todo;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// Mirrors TodoRepositoryTest exactly - same assertions, different repository -
// to demonstrate both implementations produce identical behavior.
@SpringBootTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void seedDataIsLoaded() {
        List<Todo> todos = todoRepository.findAll();
        assertThat(todos).hasSizeGreaterThanOrEqualTo(2);
    }
}
