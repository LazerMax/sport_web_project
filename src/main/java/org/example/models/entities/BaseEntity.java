package org.example.models.entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Column(columnDefinition = "uuid")
    private String id;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}