package com.codecrafters.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Fabian Dietenberger
 */
@Entity
public class TodoItem {

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
