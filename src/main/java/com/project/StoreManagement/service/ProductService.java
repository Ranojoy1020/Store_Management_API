package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Product;
import com.project.StoreManagement.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantityPerUnit(updatedProduct.getQuantityPerUnit());
        existingProduct.setUnit(updatedProduct.getUnit());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long productID) {
        productRepository.deleteById(productID);
    }
}
