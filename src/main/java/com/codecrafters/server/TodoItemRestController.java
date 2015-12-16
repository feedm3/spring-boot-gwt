package com.codecrafters.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Dietenberger
 */
@RestController()
@RequestMapping("todos")
public class TodoItemRestController {

    private final TodoItemRepository repository;

    @Autowired
    public TodoItemRestController(final TodoItemRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TodoItem> getTodoItems(@RequestParam(value = "text", required = false) String containingText) {
        return repository.findByTextContainingIgnoreCase(Optional.ofNullable(containingText).orElse(""));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> addTodoItem(@RequestBody final TodoItem item) {
        repository.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
