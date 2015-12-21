package com.codecrafters.server;

import com.codecrafters.server.domain.TodoItem;
import com.codecrafters.server.domain.TodoItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringBootGwtApplication {

    private final Logger logger = LoggerFactory.getLogger(SpringBootGwtApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGwtApplication.class, args);
    }

    @Bean
    @Autowired
    CommandLineRunner onStartUp(final TodoItemRepository repository) {
        return args -> {
            // we save 3 items to the database at startup
            final List<String> itemTexts = Arrays.asList(
                    "Learn Spring Boot",
                    "Learn GWT",
                    "Learn Java 8");

            itemTexts.forEach(text -> repository.save(new TodoItem(text)));

            final List<TodoItem> items = repository.findAll();
            logger.info("Saved " + items.size() + " todo items to the database: " + items.toString());
        };
    }
}
