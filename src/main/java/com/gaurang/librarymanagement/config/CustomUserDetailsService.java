package com.gaurang.librarymanagement.config;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final List<User> users = new ArrayList<>();

    @PostConstruct
    private void init() {
        users.add(new User("admin", "{noop}admin",
                true, true, true, true,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        users.add(new User("staff", "{noop}staff",
                true, true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_STAFF"))));

        users.add(new User("user", "{noop}user",
                true, true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
