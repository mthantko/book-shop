package com.example.bookshop.service;

import com.example.bookshop.dao.CustomerDao;
import com.example.bookshop.dao.OrderDao;
import com.example.bookshop.dao.RoleDao;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Order;
import com.example.bookshop.entity.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerDao customerDao;
    private final RoleDao roleDao;
    private final OrderDao orderDao;

    @Transactional
    public void register(Customer customer, Order order) {
        Role role = roleDao.findAllByRoleName("ROLE_USER")
                .orElseThrow(EntityNotFoundException::new);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.addRole(role);
        Order manageOrder = orderDao.save(order);
        customer.addOrder(manageOrder);
        customerDao.save(customer);

    }





}
