package com.example.bookshop.controller;

import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Order;
import com.example.bookshop.entity.PaymentMethod;
import com.example.bookshop.service.AuthService;
import com.example.bookshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;


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


        authService.register(customer, order);
        this.customer = customer;
        return "redirect:/auth/info";
    }

//    @GetMapping("/info")
//    public String checkOutInfo(Map map,
//                               @ModelAttribute("totalPrice") double totalPrice) {
//        map.put("cartItems", cartService.getCartItems());
//        map.put("totalPrice", totalPrice);
//        return "info";
//    }

    // use server to page
    private Customer customer;
    @GetMapping("/info")
    public ModelAndView checkOutInfo(ModelMap map,
                                     @ModelAttribute("totalPrice") double totalPrice) {
//        map.put("cartItems", cartService.getCartItems());
//        map.put("totalPrice", totalPrice);
        ModelAndView mv = new ModelAndView();
        mv.addObject("cartItems", cartService.getCartItems());
        mv.addObject("totalPrice", totalPrice);
        mv.addObject("customerInfo", authService
                .findCustomerInfoByCustomerName(customer.getCustomerName()));
        mv.setViewName("info");
        return mv;

    }

    //auth/login
    @GetMapping("/login")
    public String login()
    {
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