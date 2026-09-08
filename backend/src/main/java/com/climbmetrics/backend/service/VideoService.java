package com.climbmetrics.backend.service;

import com.climbmetrics.backend.entity.Climb;
import com.climbmetrics.backend.entity.Video;
import com.climbmetrics.backend.exception.NoSuchClimbException;
import com.climbmetrics.backend.exception.StorageFileNotFoundException;
import com.climbmetrics.backend.exception.UnauthorizedUserException;
import com.climbmetrics.backend.repository.ClimbRepository;
import com.climbmetrics.backend.repository.VideoRepository;
import com.climbmetrics.backend.service.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class VideoService {

    private final StorageService storageService;
    private final VideoRepository videoRepository;
    private final ClimbRepository climbRepository;

    public VideoService(
            StorageService storageService,
            VideoRepository videoRepository,
            ClimbRepository climbRepository) {

        this.storageService = storageService;
        this.videoRepository = videoRepository;
        this.climbRepository = climbRepository;
    }

    public Video uploadVideo(
            Long userId,
            Long climbId,
            MultipartFile file) {

        Climb climb = climbRepository.findById(climbId)
                .orElseThrow(NoSuchClimbException::new);

        if (!climb.getUserId().equals(userId)) {
            throw new UnauthorizedUserException();
        }

        String storageKey = storageService.store(file);

        Video video = new Video();

        video.setUserId(userId);
        video.setClimbId(climbId);
        video.setStorageKey(storageKey);
        video.setOriginalFilename(file.getOriginalFilename());
        video.setContentType(file.getContentType());
        video.setFileSize(file.getSize());
        video.setUploadedAt(LocalDateTime.now());

        return videoRepository.save(video);
    }

    public Video getVideoForUser(Long userId, Long climbId) {
        return videoRepository.findFirstByClimbIdAndUserId(climbId, userId)
                .orElseThrow();

    }
}
