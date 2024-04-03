package com.example.todo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoH2Service;

import java.util.*;

@RestController
public class TodoController {
    @Autowired
    public TodoH2Service todoService;

    @GetMapping("/todos")
    public ArrayList<Todo> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/todos/{id}")
    public Todo getTodoById(@PathVariable("id") int id) {
        return todoService.getTodoById(id);
    }

    @PostMapping("/todos")
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.addTodo(todo);

    }

    @PutMapping("/todos/{id}")
    public Todo updateTodo(@PathVariable("id") int id, @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(@PathVariable("id") int id) {
        todoService.deleteTodo(id);
    }

}
