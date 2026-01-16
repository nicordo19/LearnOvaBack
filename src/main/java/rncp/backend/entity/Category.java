package rncp.backend.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table( name = "category")
public class Category {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Category() {}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }



}
