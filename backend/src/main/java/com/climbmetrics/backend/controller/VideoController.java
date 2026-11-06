package com.climbmetrics.backend.controller;

import com.climbmetrics.backend.entity.Video;
import com.climbmetrics.backend.service.ClimbService;
import com.climbmetrics.backend.service.UserService;
import com.climbmetrics.backend.service.VideoService;
import com.climbmetrics.backend.service.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class VideoController {


    private final UserService userService;
    private final VideoService videoService;
    private final StorageService storageService;

    public VideoController(UserService userService, VideoService videoService, StorageService storageService) {
        System.out.println("VIDEO UPLOAD CONTROLLER REACHED");
        this.userService = userService;
        this.videoService = videoService;
        this.storageService = storageService;
    }

    @PostMapping("/{climbId}/video")
    public ResponseEntity<?> uploadVideo(
            Authentication authentication,
            @PathVariable Long climbId,
            @RequestParam("file") MultipartFile file) {

        System.out.println("VIDEO UPLOAD CONTROLLER REACHED");

        String email = authentication.getName();

        Long userId = userService.getUserIdByEmail(email);

        Video video = videoService.uploadVideo(
                userId,
                climbId,
                file
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(video);
    }

    @GetMapping("/videos/{climbId}")
    public ResponseEntity<Resource> getVideo(
            Authentication authentication,
            @PathVariable Long climbId) {

        String email = authentication.getName();
        Long userId = userService.getUserIdByEmail(email);

        Video video = videoService.getVideoForUser(
                userId,
                climbId
        );

        Resource resource =
                storageService.loadAsResource(video.getStorageKey());

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(video.getContentType())
                )
                .body(resource);
    }
}
