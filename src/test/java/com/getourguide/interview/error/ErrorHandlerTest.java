package com.getourguide.interview.error;

import com.getourguide.interview.dto.ErrorResponseDto;
import com.getourguide.interview.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlerTest {
    private ErrorHandler errorHandler;
    private MockHttpServletRequest request;

    @BeforeEach
    void setup() {
        this.errorHandler = new ErrorHandler();
        this.request = new MockHttpServletRequest();
        this.request.setRequestURI("/activities/999");
    }

    @Test
    void testHandleResourceNotFoundException_Returns404() {
        var exception = new ResourceNotFoundException("Activity not found");

        var response = errorHandler.handleResourceNotFoundException(exception, request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        ErrorResponseDto body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.getStatus());
        assertEquals("Not Found", body.getError());
        assertEquals("Activity not found", body.getMessage());
        assertEquals("/activities/999", body.getPath());
    }

    @Test
    void testHandleGenericException_Returns500() {
        var exception = new RuntimeException("Unexpected error");

        var response = errorHandler.handleException(exception, request);

        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());

        ErrorResponseDto body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.getStatus());
        assertEquals("Internal Server Error", body.getError());
        assertTrue(body.getMessage().contains("error"));
        assertEquals("/activities/999", body.getPath());
    }

    @Test
    void testHandleIllegalArgumentException_Returns400() {
        var exception = new IllegalArgumentException("Invalid search parameter");

        var response = errorHandler.handleIllegalArgumentException(exception, request);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

        ErrorResponseDto body = response.getBody();
        assertNotNull(body);
        assertEquals(400, body.getStatus());
        assertEquals("Bad Request", body.getError());
        assertEquals("Invalid search parameter", body.getMessage());
    }

    @Test
    void testErrorResponse_ContainsAllFields() {
        var exception = new ResourceNotFoundException("Test message");
        request.setRequestURI("/test/path");

        var response = errorHandler.handleResourceNotFoundException(exception, request);
        ErrorResponseDto body = response.getBody();

        assertNotNull(body);
        assertNotNull(body.getStatus());
        assertNotNull(body.getError());
        assertNotNull(body.getMessage());
        assertNotNull(body.getPath());
    }
}
