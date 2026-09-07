package com.climbmetrics.backend.repository;


import com.climbmetrics.backend.entity.Climb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClimbRepository extends JpaRepository<Climb, Long> {

    List<Climb> findAllByUserIdOrderByDateDescTimestampDesc(Long userId);

}
