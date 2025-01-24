package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsDto {

    private Long cartItemId;
    private CartDto  cart;
    private ProductDto productDto;
    private Integer quantity;
    private Double discount;
    private Double productPrice;
}
