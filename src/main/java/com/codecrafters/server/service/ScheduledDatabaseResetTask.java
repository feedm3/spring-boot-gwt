package com.codecrafters.server.service;

import com.codecrafters.server.domain.TodoItem;
import com.codecrafters.server.domain.TodoItemRepository;
import com.codecrafters.server.web.TodoItemRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used as scheduled task which resets the database every 15 minutes to it's initial state.
 *
 * @author Fabian Dietenberger
 */
@Component
public class ScheduledDatabaseResetTask {

    private static final String FIFTEEN_MINUTES_IN_MILLIS = "1500000";
    private static final List<String> INITIAL_TODO_ITEMS = Arrays.asList(
            "Learn Spring Boot",
            "Learn GWT",
            "Learn Java 8");

    @Value("${schedulingEnabled}")
    private boolean schedulingEnabled;

    private final Logger logger = LoggerFactory.getLogger(TodoItemRestController.class);
    private final TodoItemRepository repository;

    @Autowired
    public ScheduledDatabaseResetTask(final TodoItemRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRateString = FIFTEEN_MINUTES_IN_MILLIS)
    public void resetDatabase() {
        if (schedulingEnabled) {
            logger.info("Reset database");
            repository.deleteAll();

            INITIAL_TODO_ITEMS.forEach(text -> repository.save(new TodoItem(text)));

            final List<TodoItem> itemsInDatabase = repository.findAll();
            logger.info("Saved " + itemsInDatabase.size() + " todo items to the database: " + itemsInDatabase.toString());
        }
    }
}
