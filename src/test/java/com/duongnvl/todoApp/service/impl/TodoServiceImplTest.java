package com.duongnvl.todoApp.service.impl;

import com.duongnvl.todoApp.entity.Todo;
import com.duongnvl.todoApp.enums.TodoStatus;
import com.duongnvl.todoApp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo existingTodo;

    @BeforeEach
    void setUp() {
        existingTodo = Todo.builder()
                .id(1L)
                .title("Existing title")
                .description("Existing description")
                .status(TodoStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getAllTodosShouldReturnAllTodosFromRepository() {
        // Arrange
        List<Todo> todos = List.of(existingTodo, Todo.builder().id(2L).title("Second").description("Second description")
                .status(TodoStatus.COMPLETED).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build());
        when(todoRepository.findAll()).thenReturn(todos);

        // Act
        List<Todo> result = todoService.getAllTodos();

        // Assert
        assertEquals(todos, result);
        verify(todoRepository).findAll();
    }

    @Test
    void getTodoByIdShouldReturnTodoWhenItExists() {
        // Arrange
        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));

        // Act
        Todo result = todoService.getTodoById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(existingTodo, result);
        verify(todoRepository).findById(1L);
    }

    @Test
    void getTodoByIdShouldThrowRuntimeExceptionWhenTodoDoesNotExist() {
        // Arrange
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> todoService.getTodoById(99L));

        // Assert
        assertEquals("Todo not found", exception.getMessage());
        verify(todoRepository).findById(99L);
    }

    @Test
    void createTodoShouldSaveAndReturnCreatedTodo() {
        // Arrange
        when(todoRepository.save(existingTodo)).thenReturn(existingTodo);

        // Act
        Todo result = todoService.createTodo(existingTodo);

        // Assert
        assertEquals(existingTodo, result);
        verify(todoRepository).save(existingTodo);
    }

    @Test
    void updateTodoShouldModifyExistingTodoAndSaveIt() {
        // Arrange
        Todo updatedTodo = Todo.builder()
                .title("Updated title")
                .description("Updated description")
                .status(TodoStatus.COMPLETED)
                .build();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Todo result = todoService.updateTodo(1L, updatedTodo);

        // Assert
        assertNotNull(result);
        assertEquals("Updated title", result.getTitle());
        assertEquals("Updated description", result.getDescription());
        assertEquals(TodoStatus.COMPLETED, result.getStatus());
        verify(todoRepository).findById(1L);
        verify(todoRepository).save(existingTodo);
    }

    @Test
    void deleteTodoShouldDelegateDeletionToRepository() {
        // Arrange
        doNothing().when(todoRepository).deleteById(1L);

        // Act
        todoService.deleteTodo(1L);

        // Assert
        verify(todoRepository).deleteById(1L);
    }

    @Test
    void toggleStatusShouldChangePendingTodoToCompleted() {
        // Arrange
        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        todoService.toggleStatus(1L);

        // Assert
        assertEquals(TodoStatus.COMPLETED, existingTodo.getStatus());
        verify(todoRepository).findById(1L);
        verify(todoRepository).save(existingTodo);
    }

    @Test
    void toggleStatusShouldChangeCompletedTodoToPending() {
        // Arrange
        existingTodo.setStatus(TodoStatus.COMPLETED);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        todoService.toggleStatus(1L);

        // Assert
        assertEquals(TodoStatus.PENDING, existingTodo.getStatus());
        verify(todoRepository).findById(1L);
        verify(todoRepository).save(existingTodo);
    }

    @Test
    void searchShouldReturnMatchingTodosFromRepository() {
        // Arrange
        List<Todo> todos = List.of(existingTodo);
        when(todoRepository.findByTitleContainingIgnoreCase("study")).thenReturn(todos);

        // Act
        List<Todo> result = todoService.search("study");

        // Assert
        assertEquals(todos, result);
        verify(todoRepository).findByTitleContainingIgnoreCase("study");
    }

    @Test
    void filterByStatusShouldReturnTodosMatchingTheRequestedStatus() {
        // Arrange
        List<Todo> todos = List.of(existingTodo);
        when(todoRepository.findByStatus(TodoStatus.PENDING)).thenReturn(todos);

        // Act
        List<Todo> result = todoService.filterByStatus(TodoStatus.PENDING);

        // Assert
        assertEquals(todos, result);
        verify(todoRepository).findByStatus(TodoStatus.PENDING);
    }
}
