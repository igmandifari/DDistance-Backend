package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.service.FileService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final  Path directoryPath;
    @Autowired
    public FileServiceImpl(@Value("${app.warung-makan-bahari.directory-image-path}") String directoryPath) {
        this.directoryPath = Paths.get(directoryPath);

    }
    @PostConstruct
    public void init() {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String createFile(MultipartFile request) {
        try{
            log.info("Start createFile");
            String filename = System.currentTimeMillis() + "_" + request.getOriginalFilename();
            Path filePath = directoryPath.resolve(filename);
            Files.copy(request.getInputStream(), filePath);
            return  filePath.toString();
        }catch (IOException e) {
            log.error("Error createFile: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Resource urlImage(String path) {
        try{
            Path filepath =Paths.get(path);
            log.info(filepath.toString());
            if (!Files.exists(filepath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "image not found");
            log.info("End findByPath");

            return new UrlResource(filepath.toUri());
        }catch (MalformedURLException e) {
            log.error("Error findByPath: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
