package com.codecrafters.client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

/**
 * This class is used as rest service for the {@link TodoItem}.
 *
 * @author Fabian Dietenberger
 */
@Path("todos")
interface TodoItemService extends RestService {

    /**
     * Get all todoItems from the server.
     *
     * @param text optional text to only get the todoItems which contain the given text
     * @param callback callback
     */
    @GET
    @Path("?text={text}")
    void getTodos(@PathParam("text") final String text, MethodCallback<List<TodoItem>> callback);

    /**
     * Add a todoItem to the server.
     *
     * @param todoItem the todoItem to add
     * @param callback callback
     */
    @PUT
    void addTodo(final TodoItem todoItem, final MethodCallback<Void> callback);

    /**
     * Delete a todoItem from the server.
     *
     * @param todoItem the todoItem to delete
     * @param callback callback
     */
    @DELETE
    void deleteTodo(final TodoItem todoItem, final MethodCallback<Void> callback);
}
