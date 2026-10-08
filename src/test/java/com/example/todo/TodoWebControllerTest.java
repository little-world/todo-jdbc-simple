package com.example.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getIndexPageRendersThymeleafTemplateWithoutCssOrJs() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("todos"))
                .andExpect(model().attributeExists("todos"))
                .andExpect(model().attributeExists("newTodo"))
                .andExpect(content().string(containsString("<h1>Todo List</h1>")))
                .andExpect(content().string(containsString("<form")))
                .andExpect(content().string(not(containsString("<link rel=\"stylesheet\""))))
                .andExpect(content().string(not(containsString("<style"))))
                .andExpect(content().string(not(containsString("<script"))));
    }

    @Test
    void getTodosPageRendersSuccessfully() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(view().name("todos"))
                .andExpect(model().attributeExists("todos"));
    }

    @Test
    void createTodoSubmitsFormAndRedirects() throws Exception {
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("task", "A newly added Thymeleaf task"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todos"));

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("A newly added Thymeleaf task")));
    }
}
