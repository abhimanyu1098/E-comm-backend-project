package com.ecommerce.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long stockQuantity;
    private String imageUrl;
    private String category;
    private Boolean active;
}
