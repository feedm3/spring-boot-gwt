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
        def databaseResetTask = new ScheduledDatabaseResetTask(repository)
        databaseResetTask.schedulingEnabled = true;

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
        def databaseResetTask = new ScheduledDatabaseResetTask(repository)
        databaseResetTask.schedulingEnabled = false

        when:
        databaseResetTask.resetDatabase()

        then:
        0 * repository.deleteAll()
        0 * repository.save(_)
    }

}
