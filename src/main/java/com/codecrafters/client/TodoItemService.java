package com.codecrafters.client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    void addTodo(final TodoItem reservation, final MethodCallback<Void> callback);
}
