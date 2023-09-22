package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sku {

    @Id
    private String id;
    private double value;

    // Constructors, getters, and setters

    public Sku() {
        // Default constructor
    }

    public Sku(String id, double value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
