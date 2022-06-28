package com.nicoleozkan.pantrypal.service;

import com.nicoleozkan.pantrypal.model.User;
import com.nicoleozkan.pantrypal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    // Gets logged-in user
    public User getPrincipal()
    {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    // Encrypts user's password and saves user to database
    public void registerUser(User user)
    {
        if (user != null)
        {
            // Encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        } else
            throw new RuntimeException("User cannot be null");
    }
}