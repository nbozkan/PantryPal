package com.nicoleozkan.pantrypal.security;

import com.nicoleozkan.pantrypal.model.User;
import com.nicoleozkan.pantrypal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException
    {
        User user = repo.findByEmail(emailAddress);
        if (user == null)
        {
            throw new UsernameNotFoundException("User not found.");
        }
        return new CustomUserDetails(user);
    }
}