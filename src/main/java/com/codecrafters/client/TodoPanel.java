package com.codecrafters.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
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
    Button showTodoItemsButton;

    @UiField
    UListElement todoItemsList;

    @UiField
    TextBox todoItemTextBox;

    @UiField
    Button addTodoItemButton;

    public TodoPanel() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);

        showTodoItemsButton.addClickHandler(event -> {
            refreshTodoItems();
        });

        addTodoItemButton.addClickHandler(event -> {
            final String todoItemText = todoItemTextBox.getText();
            if (!todoItemText.isEmpty()) {
                addTodoItem(todoItemText);
            }
        });

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
                todoItemsList.removeAllChildren();
                for (final TodoItem todoItem : response) {
                    final LIElement todoListItem = Document.get().createLIElement();
                    todoListItem.setInnerHTML(todoItem.getText());
                    todoItemsList.appendChild(todoListItem);
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
}