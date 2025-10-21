package com.clinica.citas.controller;

import com.clinica.citas.entity.Paciente;
import com.clinica.citas.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping("/buscar")
    public String buscarPacientes(@RequestParam("query") String query, Model model) {
    List<Paciente> pacientes = pacienteService.buscarPacientes(query);
    System.out.println("Pacientes encontrados: " + pacientes.size());
    pacientes.forEach(p -> System.out.println(p.getNombreCompleto()));
    model.addAttribute("pacientes", pacientes);
    return "fragmentos/listaResultados :: resultadosFragment";
}

}
