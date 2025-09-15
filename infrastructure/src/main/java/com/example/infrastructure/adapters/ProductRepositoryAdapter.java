package com.example.infrastructure.adapters;

import com.example.domain.model.Product;
import com.example.domain.ports.ProductRepository;
import com.example.infrastructure.jpa.ProductEntity;
import com.example.infrastructure.jpa.ProductJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductJpaRepository jpa;

    public ProductRepositoryAdapter(ProductJpaRepository jpa) { this.jpa = jpa; }

    private static Product toDomain(ProductEntity e) {
        return new Product(e.getId(), e.getName(), e.getPrice());
    }

    private static ProductEntity toEntity(Product d) {
        return new ProductEntity(d.getId(), d.getName(), d.getPrice());
    }

    @Override
    public Product save(Product product) {
        ProductEntity saved = jpa.save(toEntity(product));
        return toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpa.findById(id).map(ProductRepositoryAdapter::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpa.findAll().stream().map(ProductRepositoryAdapter::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByName(name);
    }
}
