package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest("iphone 13", "Apple latest", BigDecimal.valueOf(1200));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequest)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());

    }

    @Test
    void getAllProducts() throws Exception {
        Product product1 = new Product(null,"iphone 13", "Apple latest", BigDecimal.valueOf(1200));
        Product product2 = new Product(null,"Galaxy S22", "Samsung's flagship", BigDecimal.valueOf(1000));
        productRepository.saveAll(List.of(product1, product2));

        // Call the endpoint and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("iphone 13")))
                .andExpect(jsonPath("$[0].description", is("Apple latest")))
                .andExpect(jsonPath("$[0].price", is(1200)))
                .andExpect(jsonPath("$[1].name", is("Galaxy S22")))
                .andExpect(jsonPath("$[1].description", is("Samsung's flagship")))
                .andExpect(jsonPath("$[1].price", is(1000)));
    }
}