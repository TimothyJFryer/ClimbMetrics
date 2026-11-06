package com.climbmetrics.backend.repository;

import com.climbmetrics.backend.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findAllByUserId(Long userId);

    List<Video> findAllByClimbId(Long climbId);

    Optional<Video> findFirstByClimbIdAndUserId(Long climbId, Long userId);

    Optional<Video> findByIdAndUserId(Long videoId, Long userId);
}
