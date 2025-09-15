package com.example.presentation.controller;

import com.example.application.dto.ProductDTO;
import com.example.application.service.ProductService;
import com.example.application.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public static record CreateRequest(@NotBlank String name, @Min(0) BigDecimal price) {}
    public static record UpdateRequest(@NotBlank String name, @Min(0) BigDecimal price) {}

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Product with this name already exists")
    })
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody CreateRequest req) {
        ProductDTO dto = service.handle(new CreateProductCommand(req.name(), req.price()));
        return ResponseEntity.created(URI.create("/api/products/" + dto.id())).body(dto);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductDTO.class)))
    public ResponseEntity<List<ProductDTO>> list() {
        return ResponseEntity.ok(service.handle(new ListProductsQuery()));
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a product by ID", description = "Returns a single product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> get(
            @Parameter(description = "ID of the product", required = true)
            @PathVariable UUID id) {
        ProductDTO dto = service.handle(new GetProductQuery(id));
        return ResponseEntity.ok(dto);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "409", description = "Product with this name already exists")
    })
    public ResponseEntity<ProductDTO> update(
            @Parameter(description = "ID of the product", required = true)
            @PathVariable UUID id, 
            @Valid @RequestBody UpdateRequest req) {
        ProductDTO dto = service.handle(new UpdateProductCommand(id, req.name(), req.price()));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the product", required = true)
            @PathVariable UUID id) {
        service.handle(new DeleteProductCommand(id));
        return ResponseEntity.noContent().build();
    }
}
