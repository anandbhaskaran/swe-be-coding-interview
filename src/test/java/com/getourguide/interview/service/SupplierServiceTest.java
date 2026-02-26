package com.getourguide.interview.service;

import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.repository.SupplierRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.getourguide.interview.helpers.SupplierHelper.createSupplier;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierServiceTest {
    private SupplierRepository supplierRepository;
    private SupplierService supplierService;

    @BeforeEach
    void setup() {
        this.supplierRepository = mock(SupplierRepository.class);
        this.supplierService = new SupplierService(supplierRepository);
    }

    @Test
    void testGetAllSuppliers() {
        var supplier1 = createSupplier(1L, "John Doe");
        var supplier2 = createSupplier(2L, "Jane Doe");

        when(supplierRepository.findAll()).thenReturn(List.of(supplier1, supplier2));

        var result = supplierService.getAllSuppliers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
    }

    @Test
    void testSearchSuppliers_ByName() {
        var supplier = createSupplier(1L, "John Doe");
        supplier.setAddress("123 Main St");
        supplier.setCity("Berlin");

        when(supplierRepository.searchSuppliers("John"))
                .thenReturn(List.of(supplier));

        var result = supplierService.searchSuppliers("John");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(supplierRepository).searchSuppliers("John");
    }

    @Test
    void testSearchSuppliers_MultipleMatches() {
        var supplier1 = createSupplier(1L, "John Doe");
        supplier1.setCity("Anytown");
        var supplier2 = createSupplier(2L, "Jane Doe");
        supplier2.setCity("Anytown");

        when(supplierRepository.searchSuppliers("Anytown"))
                .thenReturn(List.of(supplier1, supplier2));

        var result = supplierService.searchSuppliers("Anytown");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testSearchSuppliers_NoMatches() {
        when(supplierRepository.searchSuppliers("NonExistent"))
                .thenReturn(List.of());

        var result = supplierService.searchSuppliers("NonExistent");

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}