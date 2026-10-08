package com.example.todo.model;


public class Todo {

    private long id;
    private String task;

    public Todo() {
    }

    public Todo(long id, String task) {
        this.id = id;
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

