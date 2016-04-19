package com.codecrafters.server;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * This class is used to autowire the application properties into a bean.
 *
 * @author Fabian Dietenberger
 */
@Configuration
@ConfigurationProperties(prefix = "spring-boot-gwt")
public class SpringBootGwtProperties {

    private List<String> initialTodoItems;
    private boolean scheduledDatabaseReset;

    public List<String> getInitialTodoItems() {
        return initialTodoItems;
    }

    public void setInitialTodoItems(final List<String> initialTodoItems) {
        this.initialTodoItems = initialTodoItems;
    }

    public boolean isScheduledDatabaseReset() {
        return scheduledDatabaseReset;
    }

    public void setScheduledDatabaseReset(final boolean scheduledDatabaseReset) {
        this.scheduledDatabaseReset = scheduledDatabaseReset;
    }
}
