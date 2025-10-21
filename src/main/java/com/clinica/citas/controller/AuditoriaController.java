package com.clinica.citas.controller;

import com.clinica.citas.entity.Auditoria;
import com.clinica.citas.service.impl.AuditoriaServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaServiceImpl auditoriaService;

    @GetMapping
    public String listar(Model model) {
        List<Auditoria> registros = auditoriaService.listarTodas();
        model.addAttribute("registros", registros);
        return "auditoria/lista";
    }
}
