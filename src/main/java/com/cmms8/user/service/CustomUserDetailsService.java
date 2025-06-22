package com.cmms8.user.service;

import com.cmms8.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.cmms8.user.entity.User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserBuilder builder = User.withUsername(user.getUserId());
        builder.password(user.getPassword());
        builder.roles("USER");
        return builder.build();
    }
}
