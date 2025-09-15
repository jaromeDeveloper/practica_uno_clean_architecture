package com.example.application.usecase;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductCommand(UUID id, String name, BigDecimal price) {}
