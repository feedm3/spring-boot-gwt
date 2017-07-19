package com.codecrafters.server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification


/**
 * This class is used as integration test for the TodoItemRepository.
 *
 * @author Fabian Dietenberger
 */
@SpringBootTest
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
