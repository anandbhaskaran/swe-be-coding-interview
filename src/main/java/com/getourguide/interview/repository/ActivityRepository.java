package com.getourguide.interview.repository;

import com.getourguide.interview.entity.Activity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByTitleContaining(String search);
}
