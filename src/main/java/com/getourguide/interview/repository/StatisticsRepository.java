package com.getourguide.interview.repository;

import com.getourguide.interview.dto.SupplierStatsDto;
import com.getourguide.interview.entity.Supplier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Supplier, Long> {
    @Query("""
        SELECT new com.getourguide.interview.dto.SupplierStatsDto(
            s.id,
            s.name,
            COUNT(a.id),
            AVG(a.rating)
        )
        FROM Supplier s
        LEFT JOIN s.activities a
        GROUP BY s.id, s.name
        ORDER BY COUNT(a.id) DESC
        """)
    List<SupplierStatsDto> getSupplierStats();
}
