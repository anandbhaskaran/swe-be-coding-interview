package com.getourguide.interview.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ErrorHandlerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetActivityById_NotFound_Returns404WithStructuredError() {
        var response = restTemplate.getForEntity("/activities/999999", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        String body = response.getBody();
        assertNotNull(body);
        assertTrue(body.contains("\"status\":404"));
        assertTrue(body.contains("\"error\":\"Not Found\""));
        assertTrue(body.contains("Activity not found"));
        assertTrue(body.contains("\"path\":\"/activities/999999\""));
    }

    @Test
    void testGetActivityById_Valid_Returns200() {
        // Activity with ID 25651 exists in seed data
        var response = restTemplate.getForEntity("/activities/25651", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
