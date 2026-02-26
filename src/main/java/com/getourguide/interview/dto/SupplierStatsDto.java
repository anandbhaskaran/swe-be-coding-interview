package com.getourguide.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierStatsDto {
    private Long supplierId;
    private String supplierName;
    private Long activityCount;
    private Double averageRating;
}
