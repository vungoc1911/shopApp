package com.example.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "products")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 350)
    private String name;
    private float price;
    @Column(name = "thumbnail", length = 300)
    private String thumbnail;
    private String description;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
