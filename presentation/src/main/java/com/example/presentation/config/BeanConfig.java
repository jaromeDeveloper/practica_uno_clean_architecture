package com.example.presentation.config;

import com.example.application.service.ProductService;
import com.example.domain.ports.ProductRepository;
import com.example.infrastructure.adapters.ProductRepositoryAdapter;
import com.example.infrastructure.jpa.ProductJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ProductRepository productRepository(ProductJpaRepository jpa) {
        return new ProductRepositoryAdapter(jpa);
    }

    @Bean
    public ProductService productService(ProductRepository repo) {
        return new ProductService(repo);
    }
}
