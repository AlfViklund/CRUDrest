package com.arslek.CRUD3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.arslek.CRUD3.model.User;
import com.arslek.CRUD3.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class AuthController {
    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String showUser(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByName(principal.getName()));
        return "/user/show";
    }
}
