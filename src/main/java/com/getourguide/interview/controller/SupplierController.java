package com.getourguide.interview.controller;

import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.service.SupplierService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping("/suppliers")
    public ResponseEntity<List<SupplierDto>> suppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/suppliers/search/{search}")
    public ResponseEntity<List<SupplierDto>> suppliersSearch(@PathVariable String search) {
        return ResponseEntity.ok(supplierService.searchSuppliers(search));
    }
}
