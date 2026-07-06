package com.duongnvl.todoApp.service;

import com.duongnvl.todoApp.enums.TodoStatus;
import com.duongnvl.todoApp.entity.Todo;

import java.util.List;

public interface TodoService {

    List<Todo> getAllTodos();

    Todo getTodoById(Long id);

    Todo createTodo(Todo todo);

    Todo updateTodo(Long id, Todo todo);

    void deleteTodo(Long id);

    void toggleStatus(Long id);

    List<Todo> search(String keyword);

    List<Todo> filterByStatus(TodoStatus status);
}