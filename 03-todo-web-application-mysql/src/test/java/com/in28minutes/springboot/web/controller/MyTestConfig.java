package com.in28minutes.springboot.web.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
public class MyTestConfig {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User("in28minutes", "{noop}dummy",authorities);

        return new InMemoryUserDetailsManager(user);
    }
}
