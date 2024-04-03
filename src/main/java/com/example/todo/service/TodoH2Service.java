package com.example.todo.service;

import com.example.todo.model.TodoRowMapper;
import com.example.todo.repository.TodoRepository;
import com.example.todo.model.Todo;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class TodoH2Service implements TodoRepository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Todo> getTodos() {
        List<Todo> todoList = db.query("select * from todoList", new TodoRowMapper());
        ArrayList<Todo> todos = new ArrayList<>(todoList);

        return todos;
    }

    @Override
    public Todo getTodoById(int id) {
        try {
            Todo todo = db.queryForObject("select * from todoList where id = ?", new TodoRowMapper(), id);
            return todo;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Todo addTodo(Todo todo) {
        db.update("INSERT INTO todoList(todo, priority, status) values(?,?,?)", todo.getTodo(), todo.getPriority(),
                todo.getStatus());
        Todo savedTodo = db.queryForObject("SELECT * FROM todoList WHERE todo = ? and priority = ? and status = ?",
                new TodoRowMapper(), todo.getTodo(), todo.getPriority(), todo.getStatus());

        return savedTodo;
    }

    @Override
    public Todo updateTodo(int id, Todo todo) {
        if (todo.getTodo() != null) {
            db.update("update todoList set todo = ? where id = ?", todo.getTodo(), id);
        }
        if (todo.getPriority() != null) {
            db.update("update todoList set priority = ? where id = ?", todo.getPriority(), id);
        }
        if (todo.getStatus() != null) {
            db.update("update todoList set status = ? where id = ?", todo.getStatus(), id);
        }

        return getTodoById(id);
    }

    @Override
    public void deleteTodo(int id) {
        db.update("delete from todoList where id = ?", id);

    }
}
