package com.example.bookshop.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private int id;
    private String isbn;
    private String title;
    private double price;
    private int quantity;

}
