package com.clinica.citas.controller;

import com.clinica.citas.entity.Usuario;
import com.clinica.citas.service.impl.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", usuarioService.listarRoles());
        return "usuarios/registrar";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, RedirectAttributes ra) {
        try {
            usuarioService.guardar(usuario);
            ra.addFlashAttribute("success", "Usuario registrado correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.listarTodos().stream()
                .filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", usuarioService.listarRoles());
        return "usuarios/editar";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Usuario usuario, RedirectAttributes ra) {
        usuarioService.actualizar(usuario);
        ra.addFlashAttribute("success", "Usuario actualizado correctamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id, RedirectAttributes ra) {
        usuarioService.desactivar(id);
        ra.addFlashAttribute("success", "Usuario desactivado correctamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        usuarioService.eliminar(id);
        ra.addFlashAttribute("success", "Usuario eliminado correctamente.");
        return "redirect:/usuarios";
    }
}
