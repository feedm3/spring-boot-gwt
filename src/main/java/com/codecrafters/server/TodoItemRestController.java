package com.codecrafters.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This class is used as rest controller for the {@link TodoItem}.
 *
 * @author Fabian Dietenberger
 */
@RestController()
@RequestMapping("todos")
class TodoItemRestController {

    private final TodoItemRepository repository;
    private final Logger logger = LoggerFactory.getLogger(TodoItemRestController.class);

    @Autowired
    public TodoItemRestController(final TodoItemRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TodoItem>> getTodoItems(@RequestParam(value = "text", required = false) String containingText) {
        final List<TodoItem> items = repository.findByTextContainingIgnoreCase(Optional.ofNullable(containingText).orElse(""));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(items);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> addTodoItem(@RequestBody final TodoItem item) {
        repository.save(item);
        logger.info("Item saved: " + item.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> removeTodoItem(@RequestBody final TodoItem item) {
        repository.delete(item);
        logger.info("Item deleted: " + item.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
