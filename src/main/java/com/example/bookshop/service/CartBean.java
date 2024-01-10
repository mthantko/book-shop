package com.example.bookshop.service;

import com.example.bookshop.dto.CartItem;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Component
@SessionScope
public class CartBean {

    private Set<CartItem> cartItems = new HashSet<>();

    public void addCartItem(CartItem cartItem){
        cartItems.add(cartItem);
    }

    public Integer cartSize(){
        return cartItems.size();
    }

    public Set<CartItem> getCartItems(){
        return this.cartItems;
    }


}