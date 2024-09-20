package com.example.shopapp.controller;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct (@RequestPart("file") MultipartFile file) throws IOException {
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.ok("file quá lơn");
        }
        String a = storeFile(file);
        return ResponseEntity.ok("ád");
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename = UUID.randomUUID().toString() + " - " + fileName;
        java.nio.file.Path uploadDỉr = Paths.get("upload");
        if (!Files.exists(uploadDỉr)) {
            Files.createDirectories(uploadDỉr);
        }
        java.nio.file.Path destination = Paths.get(uploadDỉr.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
