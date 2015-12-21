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
 * @author Fabian Dietenberger
 */
public class TodoPanel extends Composite {

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
                    todoItemLabel.addClickHandler(todoItem1 -> removeTodoItem(todoItem1));
                    todoItemsList.add(todoItemLabel);
                }
            }
        });
    }

    private void addTodoItem(final String text) {
        final TodoItem todoItem = new TodoItem();
        todoItem.setText(text);
        todoItemService.addTodo(todoItem, new MethodCallback<Void>() {
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