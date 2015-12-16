package com.codecrafters.server
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
/**
 * @author Fabian Dietenberger
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = SpringBootGwtApplication.class)
@WebIntegrationTest
@Stepwise
class TodoItemRestControllerIntegrationTest extends Specification {

    // TODO make tests better

    @Shared
    RestTemplate restTemplate = new TestRestTemplate()

    @Autowired
    ObjectMapper mapper;

    @Shared
    def todoItem

    def "PUT /todos"() {
        when: "we post a new item"
        restTemplate.put("http://localhost:8080/todos", new TodoItem("Sample text 12354321423"), TodoItem.class)

        then: "we get the corresponding http status"
        notThrown(RestClientException)
    }

    def "GET /todos"() {
        when: "we get the items"
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/todos", String.class)

        then: "we get the corresponding http status"
        response.getStatusCode() == HttpStatus.OK

        when: "we get search for an item by the text"
        ResponseEntity<String> responseExact = restTemplate.getForEntity("http://localhost:8080/todos?text=123543", String.class)
        List<TodoItem> items = mapper.readValue(responseExact.getBody(), new TypeReference<List<TodoItem>>(){});

        then: "we find it"
        items.size() == 1
        items.get(0).getText() == "Sample text 12354321423"
    }

    def "DELETE /todos"() {
        when:
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/todos?text=123543", String.class)
        List<TodoItem> items = mapper.readValue(response.getBody(), new TypeReference<List<TodoItem>>(){});

        restTemplate.delete("http://localhost:8080/todos", items.get(0))
        HttpEntity<TodoItem> request = new HttpEntity<>(items.get(0))

        restTemplate.exchange("http://localhost:8080/todos", HttpMethod.DELETE, request, String.class)

        response = restTemplate.getForEntity("http://localhost:8080/todos?text=123543", String.class)
        items = mapper.readValue(response.getBody(), new TypeReference<List<TodoItem>>(){});

        then:
        items.size() == 0
    }
}