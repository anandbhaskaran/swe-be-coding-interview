package com.getourguide.interview.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SupplierControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllSuppliers_ReturnsDto() {
        var response = restTemplate.getForEntity("/suppliers", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();
        assertNotNull(body);
        assertTrue(body.contains("\"id\":1"));
        assertTrue(body.contains("\"name\":\"John Doe\""));
    }

    @Test
    void testSearchSuppliers_ByName() {
        var response = restTemplate.getForEntity("/suppliers/search/John", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();
        assertNotNull(body);
        assertTrue(body.contains("\"name\":\"John Doe\""));
    }

    @Test
    void testSearchSuppliers_ByCity() {
        var response = restTemplate.getForEntity("/suppliers/search/Anytown", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();
        assertNotNull(body);
        // Should find multiple suppliers in Anytown
        assertTrue(body.contains("\"city\":\"Anytown\""));
    }

    @Test
    void testSearchSuppliers_NoMatches() {
        var response = restTemplate.getForEntity("/suppliers/search/NonExistentSupplier", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();
        assertNotNull(body);
        assertEquals("[]", body);
    }

    @Test
    void testSearchSuppliers_MultipleMatches() {
        var response = restTemplate.getForEntity("/suppliers/search/Doe", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();
        assertNotNull(body);
        // Should find John Doe, Jane Doe, Charlie Doe
        assertTrue(body.contains("John Doe"));
        assertTrue(body.contains("Jane Doe"));
        assertTrue(body.contains("Charlie Doe"));
    }

    @Test
    void testSearchSuppliers_BlankSearch_Returns400() {
        var response = restTemplate.getForEntity("/suppliers/search/ ", String.class);

        // Validation should fail with 400
        assertTrue(response.getStatusCode().is4xxClientError());
    }
}
