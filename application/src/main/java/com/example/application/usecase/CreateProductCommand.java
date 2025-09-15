package com.example.application.usecase;

import java.math.BigDecimal;

public record CreateProductCommand(String name, BigDecimal price) {}
