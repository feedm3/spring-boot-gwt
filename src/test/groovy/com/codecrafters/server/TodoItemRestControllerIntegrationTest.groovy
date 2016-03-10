package com.codecrafters.server

import com.codecrafters.server.SpringBootGwtApplication
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import javax.ws.rs.core.HttpHeaders

/**
 * This class is used as integration test for the /todos url (TodoItemRestController).
 *
 * @author Fabian Dietenberger
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = SpringBootGwtApplication.class)
@WebIntegrationTest
@Stepwise
@ActiveProfiles("test")
class TodoItemRestControllerIntegrationTest extends Specification {

    @Shared
    RestTemplate restTemplate = new TestRestTemplate()

    @Autowired
    ObjectMapper mapper;

    @Shared
    String todosUrl = "http://localhost:8080/todos"

    def "PUT /todos"() {
        when: "we post a new item"
        def request = new HttpEntity<>(new TodoItem("Sample text 12354321423"))
        def response = restTemplate.exchange(todosUrl, HttpMethod.PUT, request, String.class)

        then: "we get the corresponding http status"
        response.getStatusCode() == HttpStatus.OK
    }

    def "GET /todos"() {
        when: "we get the items"
        def response = restTemplate.getForEntity(todosUrl, String.class)

        then: "we get the http status OK"
        response.getStatusCode() == HttpStatus.OK

        and: "the response should not be cached"
        response.getHeaders().get(HttpHeaders.CACHE_CONTROL).get(0) == "no-cache"
    }

    def "GET /todos?text=123543"() {
        when: "we search for an item by the text"
        def responseExact = restTemplate.getForEntity("http://localhost:8080/todos?text=123543", String.class)
        List<TodoItem> items = mapper.readValue(responseExact.getBody(), new TypeReference<List<TodoItem>>() {});

        then: "we get the item"
        items.size() == 1
        items.get(0).getText() == "Sample text 12354321423"
    }

    def "DELETE /todos"() {
        given: "the item we saved in the requests before"
        def responseBeforeDelete = restTemplate.getForEntity("http://localhost:8080/todos?text=123543", String.class)
        List<TodoItem> itemsBeforeDelete = mapper.readValue(responseBeforeDelete.getBody(), new TypeReference<List<TodoItem>>() {});

        when: "we delete the item and request them afterwards again"
        HttpEntity<TodoItem> request = new HttpEntity<>(itemsBeforeDelete.get(0))
        restTemplate.exchange(todosUrl, HttpMethod.DELETE, request, String.class)

        def responseAfterDelete = restTemplate.getForEntity("http://localhost:8080/todos?text=123543", String.class)
        List<TodoItem> itemsAfterDelete = mapper.readValue(responseAfterDelete.getBody(), new TypeReference<List<TodoItem>>() {});

        then: "the item does not exist"
        itemsAfterDelete.size() == 0
    }
}