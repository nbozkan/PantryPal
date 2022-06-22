package com.nicoleozkan.pantrypal.controller;

import com.nicoleozkan.pantrypal.model.User;
import com.nicoleozkan.pantrypal.repository.UserRepository;
import com.nicoleozkan.pantrypal.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
public class UserController
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    // Opens home page
    @GetMapping("/index")
    public String viewHomePage()
    {
        return "index";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    // Creates a user object when sent to signup form for form to reference
    @GetMapping("/signupForm")
    public String signupUser(Model model)
    {
        model.addAttribute("user", new User());
        return "signup";
    }


    // Creates a user object and adds it to the database
    @PostMapping("/processSignup")
    public String processSignup(User user)
    {
        // Add user to database
        userService.registerUser(user);

        return "login";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model)
    {
        model.addAttribute("user", userService.getPrincipal());
        return "profile";
    }

    /*@GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "index";
    }*/
}