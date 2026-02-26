package com.getourguide.interview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDto {
    private Long id;
    private String name;
    private String address;
    private String zip;
    private String city;
    private String country;
}