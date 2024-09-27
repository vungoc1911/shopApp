package com.example.shopapp.Model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at")
    private LocalDateTime created;

    @Column(name = "updated_at")
    private LocalDateTime updated;

//    @PrePersist
//    protected void onCreate() {
//        created = LocalDateTime.now();
//        updated = LocalDateTime.now();
//    }
//
//    @PrePersist
//    protected void update() {
//        updated = LocalDateTime.now();
//    }
}
