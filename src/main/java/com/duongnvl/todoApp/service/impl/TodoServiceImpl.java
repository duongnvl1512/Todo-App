package com.duongnvl.todoApp.service.impl;

import com.duongnvl.todoApp.enums.TodoStatus;
import com.duongnvl.todoApp.entity.Todo;
import com.duongnvl.todoApp.repository.TodoRepository;
import com.duongnvl.todoApp.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodo(Long id, Todo todo) {

        Todo existingTodo = getTodoById(id);

        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());

        if (todo.getStatus() != null) {
            existingTodo.setStatus(todo.getStatus());
        }

        return todoRepository.save(existingTodo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void toggleStatus(Long id) {

        Todo todo = getTodoById(id);

        if (todo.getStatus() == TodoStatus.PENDING) {
            todo.setStatus(TodoStatus.COMPLETED);
        } else {
            todo.setStatus(TodoStatus.PENDING);
        }

        todoRepository.save(todo);
    }

    @Override
    public List<Todo> search(String keyword) {
        return todoRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Todo> filterByStatus(TodoStatus status) {
        return todoRepository.findByStatus(status);
    }
}