package com.example.hellographql.common.security.service;

import com.example.hellographql.common.security.factory.JwtUserFactory;
import com.example.hellographql.user.dto.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = new User();
        user.setId(userId);
        user.setUsername(userId);
        user.setPassword("123");
        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_USER");
        user.setRoles(roleList);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userId));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
