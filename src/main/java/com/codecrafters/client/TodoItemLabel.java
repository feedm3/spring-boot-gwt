package com.codecrafters.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent a {@link TodoItem} in a panel.
 *
 * @author Fabian Dietenberger
 */
class TodoItemLabel extends Composite {

    interface TodoItemLabelUiBinder extends UiBinder<Label, TodoItemLabel> {}
    private static TodoItemLabelUiBinder ourUiBinder = GWT.create(TodoItemLabelUiBinder.class);

    public interface TodoItemLabelClickHandler {
        void onClick(final TodoItem todoItem);
    }

    private final List<TodoItemLabelClickHandler> clickHandlers;

    @UiField
    Label label;

    public TodoItemLabel(final TodoItem todoItem) {
        initWidget(ourUiBinder.createAndBindUi(this));
        label.setText(todoItem.getText());
        clickHandlers = new ArrayList<>();

        addDomHandler(event -> {
            for (final TodoItemLabelClickHandler clickHandler : clickHandlers) {
                clickHandler.onClick(todoItem);
            }
        }, ClickEvent.getType());
    }

    public void addClickHandler(final TodoItemLabelClickHandler clickHandler) {
        clickHandlers.add(clickHandler);
    }
}