package com.codecrafters.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

/**
 * @author Fabian Dietenberger
 */
public class TodoPanel extends Composite {

    private final TodoItemService service = GWT.create(TodoItemService.class);

    public TodoPanel() {
        final FlowPanel rootPanel = new FlowPanel();
        initWidget(rootPanel);

        service.getTodos("", new MethodCallback<List<TodoItem>>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                rootPanel.add(new Label(exception.getMessage()));
            }

            @Override
            public void onSuccess(final Method method, final List<TodoItem> response) {
                rootPanel.add(new Label(response.toString()));
            }
        });
    }
}
