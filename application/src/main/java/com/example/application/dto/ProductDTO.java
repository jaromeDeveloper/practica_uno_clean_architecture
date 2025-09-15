package com.example.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(UUID id, String name, BigDecimal price) {}
