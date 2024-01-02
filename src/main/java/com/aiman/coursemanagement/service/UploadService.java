package com.aiman.coursemanagement.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadService {

    private static final Path uploadsDir = Path.of("uploads");

    public String uploadFile(MultipartFile file, String urlPrefix, String dir) throws IOException {
        Path filePath = uploadsDir.resolve(Path.of(dir, file.getOriginalFilename()));
        File parentDir = filePath.getParent().toFile();
        if (!parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create directory " + parentDir);
            };
        }
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return urlPrefix + "?path=" + URLEncoder.encode(filePath.toString(), StandardCharsets.UTF_8);
    }


    public ResponseEntity<Resource> downloadFile(String path) throws IOException {
        // Decode the file name
        String decodedFileName = URLDecoder.decode(path, StandardCharsets.UTF_8);
        Path filePath = Paths.get(decodedFileName);

        // Check if the file exists
        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
            // Handle non-existent file
            // You may want to log or return an error response
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF) // Set the appropriate content type
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
