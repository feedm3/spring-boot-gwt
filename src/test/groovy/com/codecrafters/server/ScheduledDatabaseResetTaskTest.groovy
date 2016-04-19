package com.codecrafters.server

import spock.lang.Specification
/**
 * This class is used as test for the ScheduledDatabaseResetTask.
 *
 * @author Fabian Dietenberger
 */
class ScheduledDatabaseResetTaskTest extends Specification {


    def "reset the database when scheduling is enabled"() {
        given:
        def repository = Mock(TodoItemRepository)
        def properties = new SpringBootGwtProperties()
        properties.scheduledDatabaseReset = true
        properties.initialTodoItems = ["Item 1", "Item 2", "Item 3"]

        def databaseResetTask = new ScheduledDatabaseResetTask(repository, properties)

        when:
        databaseResetTask.resetDatabase()

        then:
        1 * repository.deleteAll()
        3 * repository.save(_)
        1 * repository.findAll()
    }

    def "do nothing when scheduling is not enabled"() {
        given:
        def repository = Mock(TodoItemRepository)
        def properties = new SpringBootGwtProperties()
        properties.scheduledDatabaseReset = false

        def databaseResetTask = new ScheduledDatabaseResetTask(repository, properties)

        when:
        databaseResetTask.resetDatabase()

        then:
        0 * repository.deleteAll()
        0 * repository.save(_)
    }

}
