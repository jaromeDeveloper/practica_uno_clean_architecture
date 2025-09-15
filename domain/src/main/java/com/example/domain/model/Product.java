package com.example.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public final class Product {
    private final UUID id;
    private final String name;
    private final BigDecimal price;

    public Product(UUID id, String name, BigDecimal price) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (price == null || price.signum() < 0) throw new IllegalArgumentException("price must be >= 0");
        this.id = id == null ? UUID.randomUUID() : id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }

    public Product withName(String newName) { return new Product(this.id, newName, this.price); }
    public Product withPrice(BigDecimal newPrice) { return new Product(this.id, this.name, newPrice); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
