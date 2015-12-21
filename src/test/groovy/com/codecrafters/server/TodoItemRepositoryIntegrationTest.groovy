package com.codecrafters.server

import com.codecrafters.server.domain.TodoItem
import com.codecrafters.server.domain.TodoItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * This class is used as integration test for the TodoItemRepository.
 *
 * @author Fabian Dietenberger
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = SpringBootGwtApplication.class)
@IntegrationTest
@ActiveProfiles("test")
class TodoItemRepositoryIntegrationTest extends Specification {

    @Autowired
    TodoItemRepository repository

    def "Save a todo item"() {
        given: "an inital number of items in the database"
        def numberOfTodoItems = repository.findAll().size()

        when: "when we save a new item"
        repository.save(new TodoItem("Test"))

        then: "we have one more item in the database"
        repository.findAll().size() == numberOfTodoItems + 1
    }

    def "Find a todo item by containing text"() {
        given: "a new item in the database"
        repository.save(new TodoItem("I should start learning Java 8"))

        when: "we search for the specific text of the item"
        List<TodoItem> todoItems = repository.findByTextContainingIgnoreCase("learning")

        then: "we find it"
        todoItems != null
        todoItems.size() > 0
    }
}
