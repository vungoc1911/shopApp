package com.example.shopapp.controller;

import ch.qos.logback.core.util.StringUtil;
import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.model.Product;
import com.example.shopapp.response.ResponseObject;
import com.example.shopapp.services.product.IProductService;
import com.example.shopapp.services.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct (@Valid @RequestBody ProductDTO productDTO) throws IOException {
        Product newProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Create new product successfully")
                        .status(HttpStatus.CREATED)
                        .data(newProduct)
                        .build());
    }

    private String storeFile(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFilename = UUID.randomUUID().toString() + " - " + fileName;
            java.nio.file.Path uploadDỉr = Paths.get("upload");
            if (!Files.exists(uploadDỉr)) {
                Files.createDirectories(uploadDỉr);
            }
            // Lấy đường dẫn đầy đủ file
            java.nio.file.Path destination = Paths.get(uploadDỉr.toString(), uniqueFilename);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        }
        return "sakjfs";
    }
}
