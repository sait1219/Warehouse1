package com.example.cloudmanagementsystem.entity;


import jakarta.persistence.*;

@Entity  // ✅ This marks it as a JPA Entity
@Table(name = "product")  // ✅ Optional: Specifies the table name
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ✅ Correct annotation
    private Long id;

    private String name;
    private int quantity;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
