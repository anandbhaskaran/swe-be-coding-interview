package com.getourguide.interview.service;

import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.repository.SupplierRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public List<Supplier> searchSuppliers(String search) {
        return supplierRepository.searchSuppliers(search);
    }
}
