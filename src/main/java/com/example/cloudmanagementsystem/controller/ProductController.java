package com.example.cloudmanagementsystem.controller;

import com.example.cloudmanagementsystem.entity.Product;
import com.example.cloudmanagementsystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // ✅ Use @Controller instead of @RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";  // ✅ Ensure this matches the HTML file in `/templates/`
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name, 
                             @RequestParam int quantity) {
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        productService.addProduct(product);
        return "redirect:/products";  // ✅ Redirects to the updated product list
    }
}
