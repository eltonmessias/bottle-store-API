package com.bigbrother.bottleStore.product.Category;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter @Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    private String description;


    public Category(CategoryRequest request) {
        this.name = request.name();
        this.description = request.description();
    }
}
