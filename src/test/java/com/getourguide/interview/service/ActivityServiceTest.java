package com.getourguide.interview.service;

import com.getourguide.interview.repository.ActivityRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.getourguide.interview.helpers.ActivityHelper.createActivity;
import static com.getourguide.interview.helpers.SupplierHelper.createSupplier;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceTest {
    private ActivityRepository activityRepository;
    private ActivityService activityService;

    @BeforeEach
    void setup() {
        this.activityRepository = mock(ActivityRepository.class);
        this.activityService = new ActivityService(activityRepository);
    }

    @Test
    void testGetActivities() {
        var testActivity = createActivity(
            1L,
            "Test Activity",
            100,
            5.0,
            false,
            createSupplier(1L, "Test Supplier")
        );
        when(activityRepository.findAll()).thenReturn(List.of(testActivity));

        var result = activityService.getActivities();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Activity", result.get(0).getTitle());
    }

    @Test
    void testGetActivitiesById_ShouldUseFindById() {
        var supplier = createSupplier(1L, "Test Supplier");
        var testActivity = createActivity(1L, "Berlin Tour", 100, 4.5, false, supplier);

        when(activityRepository.findById(1L)).thenReturn(Optional.of(testActivity));

        var result = activityService.getActivities(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Berlin Tour", result.getTitle());
        assertEquals("Test Supplier", result.getSupplierName());
        verify(activityRepository).findById(1L);
        verify(activityRepository, never()).findAll();
    }

    @Test
    void testGetActivitiesById_WithNullSupplier() {
        var testActivity = createActivity(1L, "Berlin Tour", 100, 4.5, false, null);

        when(activityRepository.findById(1L)).thenReturn(Optional.of(testActivity));

        var result = activityService.getActivities(1L);

        assertNotNull(result);
        assertEquals("", result.getSupplierName());
    }

    @Test
    void testSearchActivities_ShouldUseRepositoryQuery() {
        var supplier = createSupplier(1L, "Test Supplier");
        var activity1 = createActivity(1L, "Berlin Museum Tour", 50, 4.8, false, supplier);
        var activity2 = createActivity(2L, "Berlin Walking Tour", 30, 4.5, true, supplier);

        when(activityRepository.findByTitleContaining("Berlin"))
            .thenReturn(List.of(activity1, activity2));

        var result = activityService.searchActivities("Berlin");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Berlin Museum Tour", result.get(0).getTitle());
        assertEquals("Berlin Walking Tour", result.get(1).getTitle());
        verify(activityRepository).findByTitleContaining("Berlin");
        verify(activityRepository, never()).findAll();
    }

    @Test
    void testSearchActivities_NoResults() {
        when(activityRepository.findByTitleContaining("NonExistent")).thenReturn(List.of());

        var result = activityService.searchActivities("NonExistent");

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
