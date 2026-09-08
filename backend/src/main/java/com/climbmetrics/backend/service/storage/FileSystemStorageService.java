package com.climbmetrics.backend.service.storage;

import com.climbmetrics.backend.exception.StorageException;
import com.climbmetrics.backend.exception.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService() {
        this.rootLocation = Paths.get("uploads");
    }

    @Override
    public String store(MultipartFile file) {

        if (file.isEmpty()) {
            throw new StorageException("Cannot store empty file");
        }

        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            Files.createDirectories(rootLocation);

            Path destination = rootLocation.resolve(filename);

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return filename;

        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {

        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new StorageFileNotFoundException(
                    "Could not read file: " + filename
            );

        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException(
                    "Could not read file: " + filename,
                    e
            );
        }
    }

    @Override
    public void delete(String filename) {

        try {
            Files.deleteIfExists(rootLocation.resolve(filename));
        } catch (IOException e) {
            throw new StorageException("Failed to delete file", e);
        }
    }
}
