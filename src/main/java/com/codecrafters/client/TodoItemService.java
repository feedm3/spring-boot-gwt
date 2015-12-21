package com.codecrafters.client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

/**
 * @author Fabian Dietenberger
 */
@Path("todos")
public interface TodoItemService extends RestService {

    @GET
    @Path("?text={text}")
    void getTodos(@PathParam("text") final String text, MethodCallback<List<TodoItem>> callback);

    @PUT
    void addTodo(final TodoItem todoItem, final MethodCallback<Void> callback);

    @DELETE
    void deleteTodo(final TodoItem todoItem, final MethodCallback<Void> callback);
}
