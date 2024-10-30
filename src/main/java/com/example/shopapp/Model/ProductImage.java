package com.example.shopapp.Model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "product_images")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "image_uri")
    private String imageUri;
}
