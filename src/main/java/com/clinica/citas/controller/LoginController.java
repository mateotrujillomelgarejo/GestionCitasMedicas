package com.clinica.citas.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Authentication auth, Model model) {
        model.addAttribute("usuario", auth.getName());

        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"))) {
            model.addAttribute("rol", "ADMINISTRADOR");
            return "dashboard/admin";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CAJERO"))) {
            model.addAttribute("rol", "CAJERO");
            return "dashboard/cajero";
        } else {
            model.addAttribute("rol", "RECEPCIONISTA");
            return "dashboard/recepcionista";
        }
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/403";
    }
}
