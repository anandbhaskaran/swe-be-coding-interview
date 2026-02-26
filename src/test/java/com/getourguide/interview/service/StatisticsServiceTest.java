package com.getourguide.interview.service;

import com.getourguide.interview.dto.SupplierStatsDto;
import com.getourguide.interview.repository.StatisticsRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticsServiceTest {
    private StatisticsRepository statisticsRepository;
    private StatisticsService statisticsService;

    @BeforeEach
    void setup() {
        this.statisticsRepository = mock(StatisticsRepository.class);
        this.statisticsService = new StatisticsService(statisticsRepository);
    }

    @Test
    void testGetSupplierStats_ReturnsDto() {
        var stats1 = new SupplierStatsDto(1L, "John Doe", 5L, 4.6);
        var stats2 = new SupplierStatsDto(2L, "Jane Doe", 3L, 4.8);

        when(statisticsRepository.getSupplierStats()).thenReturn(List.of(stats1, stats2));

        var result = statisticsService.getSupplierStats();

        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify it returns SupplierStatsDto
        SupplierStatsDto dto = result.get(0);
        assertEquals(1L, dto.getSupplierId());
        assertEquals("John Doe", dto.getSupplierName());
        assertEquals(5L, dto.getActivityCount());
        assertEquals(4.6, dto.getAverageRating());
    }

    @Test
    void testGetSupplierStats_CalculatesActivityCount() {
        var stats = new SupplierStatsDto(1L, "Test Supplier", 10L, 4.5);

        when(statisticsRepository.getSupplierStats()).thenReturn(List.of(stats));

        var result = statisticsService.getSupplierStats();

        assertEquals(10L, result.get(0).getActivityCount());
        verify(statisticsRepository).getSupplierStats();
    }

    @Test
    void testGetSupplierStats_CalculatesAverageRating() {
        var stats = new SupplierStatsDto(1L, "Test Supplier", 5L, 4.75);

        when(statisticsRepository.getSupplierStats()).thenReturn(List.of(stats));

        var result = statisticsService.getSupplierStats();

        assertEquals(4.75, result.get(0).getAverageRating());
    }

    @Test
    void testGetSupplierStats_IncludesSuppliersWithNoActivities() {
        var statsWithActivities = new SupplierStatsDto(1L, "Active Supplier", 5L, 4.5);
        var statsNoActivities = new SupplierStatsDto(3L, "Inactive Supplier", 0L, null);

        when(statisticsRepository.getSupplierStats())
                .thenReturn(List.of(statsWithActivities, statsNoActivities));

        var result = statisticsService.getSupplierStats();

        assertEquals(2, result.size());

        // Supplier with no activities should have count=0 and null rating
        SupplierStatsDto inactive = result.get(1);
        assertEquals(0L, inactive.getActivityCount());
        assertNull(inactive.getAverageRating());
    }
}
