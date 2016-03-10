package com.codecrafters.server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class is used as entity for a todoItem.
 *
 * @author Fabian Dietenberger
 */
@Entity
class TodoItem {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    public TodoItem() {
    }

    public TodoItem(final String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
