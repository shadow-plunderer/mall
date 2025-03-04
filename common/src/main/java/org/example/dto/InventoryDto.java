package org.example.dto;

import lombok.Data;

@Data
public class InventoryDto {
    private Integer id;
    private Integer productId;
    private Integer stock;
    private Integer reservedStock;
}
