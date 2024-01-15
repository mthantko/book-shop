package com.example.bookshop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CustomerOrder {

    private String customerName;
    private String email;
    private String phoneNumber;
    private String shippingAddress;
    private String billingAddress;

    private CustomerOrder(){}

    public CustomerOrder(String customerName, String email, String phoneNumber, String shippingAddress, String billingAddress) {
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }
}
