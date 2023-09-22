package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SubCategory {

    @Id
    private String id;

    // Constructors, getters, and setters

    public SubCategory() {
        // Default constructor
    }

    public SubCategory(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
