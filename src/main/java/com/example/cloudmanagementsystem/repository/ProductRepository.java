package com.example.cloudmanagementsystem.repository;

import com.example.cloudmanagementsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
