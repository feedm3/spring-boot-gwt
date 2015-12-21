package com.codecrafters.client;

/**
 * This class is used to represent a todoItem.
 *
 * @author Fabian Dietenberger
 */
class TodoItem {

    private long id;
    private String text;

    public TodoItem() {
    }

    public TodoItem(final String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
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
