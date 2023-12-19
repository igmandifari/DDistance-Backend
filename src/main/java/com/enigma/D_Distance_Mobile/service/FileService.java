package com.enigma.D_Distance_Mobile.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;


public interface FileService {
    String createFile(MultipartFile request);
    Resource urlImage(String path);
}
