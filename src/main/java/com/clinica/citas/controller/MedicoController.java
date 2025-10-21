package com.clinica.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clinica.citas.service.MedicoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MedicoController {
    
    private final MedicoService medicoService;

    @GetMapping("/buscar")
    public String buscarMedicos(@RequestParam("query") String query, Model model) {
    model.addAttribute("medicos", medicoService.buscarMedicos(query));
    return "fragmentos/listaMedicos :: resultadosFragment";
}

}
