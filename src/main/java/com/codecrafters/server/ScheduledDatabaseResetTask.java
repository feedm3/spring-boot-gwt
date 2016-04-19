package com.codecrafters.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class is used as scheduled task which resets the database every 15 minutes to it's initial state.
 *
 * @author Fabian Dietenberger
 */
@Component
class ScheduledDatabaseResetTask {

    private final Logger logger = LoggerFactory.getLogger(ScheduledDatabaseResetTask.class);
    private final TodoItemRepository repository;
    private final SpringBootGwtProperties springBootGwtProperties;

    @Autowired
    public ScheduledDatabaseResetTask(final TodoItemRepository repository, final SpringBootGwtProperties springBootGwtProperties) {
        this.repository = repository;
        this.springBootGwtProperties = springBootGwtProperties;
    }

    @Scheduled(fixedRateString = "${spring-boot-gwt.scheduled-database-reset-interval-millis}")
    public void resetDatabase() {
        if (springBootGwtProperties.isScheduledDatabaseReset()) {
            logger.info("Reset database");

            repository.deleteAll();

            for (final String initialTodoItem : springBootGwtProperties.getInitialTodoItems()) {
                repository.save(new TodoItem(initialTodoItem));
            }

            final List<TodoItem> itemsInDatabase = Optional.ofNullable(repository.findAll()).orElse(Collections.emptyList());
            logger.info("Saved " + itemsInDatabase.size() + " todo items to the database: " + itemsInDatabase.toString());
        }
    }
}
