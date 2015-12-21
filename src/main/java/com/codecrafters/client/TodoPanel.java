package com.codecrafters.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

/**
 * This class is used as root panel and contains the whole page.
 *
 * @author Fabian Dietenberger
 */
class TodoPanel extends Composite {

    interface TestViewUiBinder extends UiBinder<HTMLPanel, TodoPanel> {}
    private static TestViewUiBinder ourUiBinder = GWT.create(TestViewUiBinder.class);

    private static final TodoItemService todoItemService = GWT.create(TodoItemService.class);

    @UiField
    FlowPanel todoItemsList;

    @UiField
    TextBox todoItemTextBox;

    @UiField
    Button addTodoItemButton;

    public TodoPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
        refreshTodoItems();

        addTodoItemButton.addClickHandler(event -> {
            final String todoItemText = todoItemTextBox.getText();
            if (!todoItemText.isEmpty()) {
                addTodoItem(todoItemText);
            }
        });

        todoItemTextBox.getElement().setAttribute("placeholder", "Add a todo item");
        todoItemTextBox.addKeyUpHandler(event -> {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                final String todoItemText = todoItemTextBox.getText();
                if (!todoItemText.isEmpty()) {
                    addTodoItem(todoItemText);
                }
            }
        });
    }

    /**
     * Clear the todoItemsPanel and add all todoItems from the server.
     */
    private void refreshTodoItems() {
        todoItemService.getTodos("", new MethodCallback<List<TodoItem>>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {

            }

            @Override
            public void onSuccess(final Method method, final List<TodoItem> response) {
                todoItemsList.clear();
                for (final TodoItem todoItem : response) {
                    final TodoItemLabel todoItemLabel = new TodoItemLabel(todoItem);
                    todoItemLabel.addClickHandler(todoItemToRemove -> removeTodoItem(todoItemToRemove));
                    todoItemsList.add(todoItemLabel);
                }
            }
        });
    }

    /**
     * Send a new todoItem to the server. On success refresh the todoItemsPanel.
     *
     * @param text the text of the todoItem
     */
    private void addTodoItem(final String text) {
        todoItemService.addTodo(new TodoItem(text), new MethodCallback<Void>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {

            }

            @Override
            public void onSuccess(final Method method, final Void response) {
                todoItemTextBox.setText("");
                refreshTodoItems();
            }
        });
    }

    /**
     * Remove a todoItem from the server. On success refresh the todoItemsPanel.
     *
     * @param todoItem the todoItem to delete
     */
    public void removeTodoItem(final TodoItem todoItem) {
        todoItemService.deleteTodo(todoItem, new MethodCallback<Void>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {

            }

            @Override
            public void onSuccess(final Method method, final Void response) {
                refreshTodoItems();
            }
        });
    }
}