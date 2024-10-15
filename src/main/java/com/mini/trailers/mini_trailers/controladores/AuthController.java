package com.mini.trailers.mini_trailers.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.mini.trailers.mini_trailers.Entidades.User;
import com.mini.trailers.mini_trailers.Servicio.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login";
    }
}
