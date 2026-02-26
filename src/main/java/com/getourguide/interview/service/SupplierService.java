package com.getourguide.interview.service;

import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.repository.SupplierRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<SupplierDto> searchSuppliers(String search) {
        return supplierRepository.searchSuppliers(search).stream()
                .map(this::mapToDto)
                .toList();
    }

    private SupplierDto mapToDto(Supplier supplier) {
        return SupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .zip(supplier.getZip())
                .city(supplier.getCity())
                .country(supplier.getCountry())
                .build();
    }
}
