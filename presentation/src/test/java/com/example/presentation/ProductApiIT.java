package com.example.presentation;

import com.example.presentation.controller.ProductController.CreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductApiIT {

    @Autowired
    TestRestTemplate rest;

    @Test
    void post_then_get_list() {
        // POST create
        CreateRequest req = new CreateRequest("Laptop", new BigDecimal("25000.00"));
        ResponseEntity<Map> post = rest.postForEntity("/api/products", req, Map.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(post.getBody()).containsKeys("id", "name", "price");
        assertThat(post.getBody().get("name")).isEqualTo("Laptop");

        // GET list
        ResponseEntity<Map[]> list = rest.getForEntity("/api/products", Map[].class);
        assertThat(list.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(list.getBody()).isNotEmpty();
        assertThat(list.getBody()[0]).containsKeys("id","name","price");
    }
}
