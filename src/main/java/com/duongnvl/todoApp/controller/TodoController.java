package com.duongnvl.todoApp.controller;

import com.duongnvl.todoApp.enums.TodoStatus;
import com.duongnvl.todoApp.dto.TodoRequest;
import com.duongnvl.todoApp.entity.Todo;
import com.duongnvl.todoApp.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String home(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) TodoStatus status,
            Model model) {

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("todos", todoService.search(keyword));
        } else if (status != null) {
            model.addAttribute("todos", todoService.filterByStatus(status));
        } else {
            model.addAttribute("todos", todoService.getAllTodos());
        }

        // Thống kê
        model.addAttribute("total", todoService.getAllTodos().size());

        model.addAttribute("pending",
                todoService.filterByStatus(TodoStatus.PENDING).size());

        model.addAttribute("completed",
                todoService.filterByStatus(TodoStatus.COMPLETED).size());

        model.addAttribute("todoRequest", new TodoRequest());

        return "index";
    }

    @PostMapping("/add")
    public String addTodo(
            @Valid @ModelAttribute TodoRequest todoRequest,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute("todos", todoService.getAllTodos());

            return "index";
        }

        Todo todo = Todo.builder()
                .title(todoRequest.getTitle())
                .description(todoRequest.getDescription())
                .status(TodoStatus.PENDING)
                .build();

        todoService.createTodo(todo);

        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateTodo(
            @PathVariable Long id,
            @ModelAttribute TodoRequest todoRequest) {

        Todo todo = Todo.builder()
                .title(todoRequest.getTitle())
                .description(todoRequest.getDescription())
                .build();

        todoService.updateTodo(id, todo);

        return "redirect:/";
    }

    @GetMapping("/toggle/{id}")
    public String toggle(@PathVariable Long id) {

        todoService.toggleStatus(id);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        todoService.deleteTodo(id);

        return "redirect:/";
    }

}