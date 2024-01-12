package com.example.bookshop.Security;

import com.example.bookshop.dao.CustomerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerDao customerDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerDao
                .findCustomerByCustomerName(username)
                .map(SecurityCustomer::new)
                .orElseThrow();
    }
}
