package com.duongnvl.todoApp.repository;

import com.duongnvl.todoApp.enums.TodoStatus;
import com.duongnvl.todoApp.entity.Todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByStatus(TodoStatus status);

    List<Todo> findByTitleContainingIgnoreCase(String keyword);

}