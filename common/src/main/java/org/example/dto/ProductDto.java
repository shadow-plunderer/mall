package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private Integer brandId;
    private Integer categoryId;
    private BigDecimal price;
    private Integer status;
    private String type;
}
