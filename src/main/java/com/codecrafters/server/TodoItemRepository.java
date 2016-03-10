package com.codecrafters.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is used as repository for the {@link TodoItem}. It gives us some "ready-to-use" CRUD operations against the
 * database.
 *
 * @author Fabian Dietenberger
 */
@Repository
interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findByTextContainingIgnoreCase(final String text);
}
