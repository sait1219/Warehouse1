package com.example.cloudmanagementsystem.service;

import com.example.cloudmanagementsystem.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.*;
import com.google.api.gax.paging.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
  
public class ProductService {

	@Autowired
	private Storage storage;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    public void listBuckets() {
        storage.list().iterateAll().forEach(bucket -> System.out.println(bucket.getName()));
    }
    @Value("${gcp.bucket.name}")
    private String bucketName;

    public ProductService() {
        this.storage = StorageOptions.getDefaultInstance().getService();
        this.bucketName = "product_db"; // Your Google Cloud Storage bucket name
    }

    public void addProduct(Product product) {
        try {
            String productJson = objectMapper.writeValueAsString(product);
            BlobId blobId = BlobId.of(bucketName, "products/" + product.getId() + ".json");
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/json").build();
            storage.create(blobInfo, productJson.getBytes(StandardCharsets.UTF_8));
            System.out.println("Product stored in Google Cloud Storage: " + product.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix("products/"));
            for (Blob blob : blobs.iterateAll()) {
                String json = new String(blob.getContent(), StandardCharsets.UTF_8);
                Product product = objectMapper.readValue(json, Product.class);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
