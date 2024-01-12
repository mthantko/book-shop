package com.example.bookshop.controller;

import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Order;
import com.example.bookshop.entity.PaymentMethod;
import com.example.bookshop.service.AuthService;
import com.example.bookshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final CartService cartService;

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/save-customer")
    public String SaveCustomer(@RequestParam("billingAddress") String billingAddress,
                               @RequestParam("shippingAddress") String shippingAddress,
                               @RequestParam("payment")PaymentMethod method,
                               @ModelAttribute("totalPrice") double totalPrice,
                               Customer customer,
                               BindingResult result) {
        Order order = new  Order(
                LocalDate.now(),
                billingAddress,
                shippingAddress,
                method,
                totalPrice
        );
        if (result.hasErrors()) {
            return "register";
        }
        System.out.println("BillingAddress " + billingAddress);
        System.out.println("Payment " + method);
        System.out.println("========= " + customer);


        //authService.register(customer, order);
        return "redirect:/auth/info";
    }

    @GetMapping("/info")
    public String checkOutInfo() {
        return "info";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ModelAttribute("totalPrice")
    public double totalAmount() {
        return cartService
                .getCartItems()
                .stream()
                .map(c -> c.getQuantity() * c.getPrice())
                .reduce((a, b) -> a * b)
                .get();
    }

}