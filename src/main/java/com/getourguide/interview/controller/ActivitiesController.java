package com.getourguide.interview.controller;

import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.service.ActivityService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
@Validated
public class ActivitiesController {

    private final ActivityService activityService;

    @GetMapping("/activities")
    public ResponseEntity<List<ActivityDto>> activities() {
        return ResponseEntity.ok(activityService.getActivities());
    }

    @GetMapping("/activities/{id}")
    public ResponseEntity<ActivityDto> activities(@PathVariable @Min(value = 1, message = "Activity ID must be greater than 0") Long id) {
        return ResponseEntity.ok(activityService.getActivities(id));
    }

    @GetMapping("/activities/search/{search}")
    public ResponseEntity<List<ActivityDto>> activitiesSearch(@PathVariable @NotBlank(message = "Search term cannot be blank") String search) {
        return ResponseEntity.ok(activityService.searchActivities(search));
    }
}
