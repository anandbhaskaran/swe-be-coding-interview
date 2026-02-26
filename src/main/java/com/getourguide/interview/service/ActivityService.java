package com.getourguide.interview.service;

import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.repository.ActivityRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    public List<ActivityDto> getActivities() {
        return activityRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public ActivityDto getActivities(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        return mapToDto(activity);
    }

    public List<ActivityDto> searchActivities(String search) {
        return activityRepository.findByTitleContaining(search).stream()
                .map(this::mapToDto)
                .toList();
    }

    private ActivityDto mapToDto(Activity activity) {
        return ActivityDto.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .price(activity.getPrice())
                .currency(activity.getCurrency())
                .rating(activity.getRating())
                .specialOffer(activity.isSpecialOffer())
                .supplierName(Objects.isNull(activity.getSupplier()) ? "" : activity.getSupplier().getName())
                .build();
    }
}
