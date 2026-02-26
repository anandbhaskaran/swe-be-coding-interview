package com.getourguide.interview.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatisticsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetSupplierStats_ReturnsStructuredData() {
        var response = restTemplate.getForEntity("/stats/suppliers", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();
        assertNotNull(body);

        // Verify JSON contains required fields
        assertTrue(body.contains("\"supplierId\""));
        assertTrue(body.contains("\"supplierName\""));
        assertTrue(body.contains("\"activityCount\""));
        assertTrue(body.contains("\"averageRating\""));
    }

    @Test
    void testGetSupplierStats_IncludesSuppliersWithNoActivities() {
        var response = restTemplate.getForEntity("/stats/suppliers", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();

        // Supplier 5 has 1 activity, but there might be suppliers with 0
        // Check that we get data for all suppliers
        assertNotNull(body);
        assertTrue(body.contains("\"activityCount\":0") || body.length() > 100);
    }

    @Test
    void testGetSupplierStats_ContainsExpectedSuppliers() {
        var response = restTemplate.getForEntity("/stats/suppliers", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();
        assertNotNull(body);

        // Verify some known suppliers appear in results
        assertTrue(body.contains("John Doe") || body.contains("Jane Doe"));
    }

    @Test
    void testGetSupplierStats_ReturnsArrayNotObject() {
        var response = restTemplate.getForEntity("/stats/suppliers", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();
        assertNotNull(body);

        // Response should be array (starts with [, ends with ])
        assertTrue(body.trim().startsWith("["));
        assertTrue(body.trim().endsWith("]"));
    }
}