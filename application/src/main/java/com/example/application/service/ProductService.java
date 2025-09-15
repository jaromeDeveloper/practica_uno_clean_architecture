package com.example.application.service;

import com.example.application.dto.ProductDTO;
import com.example.application.usecase.*;
import com.example.domain.model.Product;
import com.example.domain.ports.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) { this.repo = repo; }

    public ProductDTO handle(CreateProductCommand cmd) {
        if (repo.existsByName(cmd.name())) {
            throw new IllegalArgumentException("Product name already exists");
        }
        Product saved = repo.save(new Product(null, cmd.name(), cmd.price()));
        return toDTO(saved);
    }

    public ProductDTO handle(UpdateProductCommand cmd) {
        Product existing = repo.findById(cmd.id())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Product updated = repo.save(new Product(existing.getId(), cmd.name(), cmd.price()));
        return toDTO(updated);
    }

    public ProductDTO handle(GetProductQuery query) {
        return repo.findById(query.id())
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<ProductDTO> handle(ListProductsQuery query) {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void handle(DeleteProductCommand cmd) {
        repo.deleteById(cmd.id());
    }

    private ProductDTO toDTO(Product p) {
        return new ProductDTO(p.getId(), p.getName(), p.getPrice());
    }
}
