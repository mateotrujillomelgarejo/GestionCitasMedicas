package com.clinica.citas.controller;

import com.clinica.citas.entity.Cita;
import com.clinica.citas.service.CitaService;
import com.clinica.citas.service.MedicoService;
import com.clinica.citas.service.PacienteService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;

    @GetMapping("/registrar")
    public String mostrarRegistrarCita(Model model) {
        Cita cita = new Cita();

    String ultimoCodigo = citaService.obtenerUltimoCodigoCita();
    String nuevoCodigo = generarNuevoCodigo(ultimoCodigo);

    cita.setCodigoCita(nuevoCodigo);
    cita.setFechaRegistro(java.time.LocalDate.now());

    model.addAttribute("cita", cita);
    model.addAttribute("pacientes", pacienteService.listarPacientes());
    model.addAttribute("citas", citaService.listarCitas());
    model.addAttribute("medicos", medicoService.listarTodos());
    model.addAttribute("fechaActual", java.time.LocalDate.now());
    return "citas/registrar";
    }

    @PostMapping("/registrar")
    public String registrarCita(@ModelAttribute Cita cita) {
        citaService.registrarCita(cita);
        return "redirect:/citas/registrar";
    }

    @GetMapping("/consultar")
    public String mostrarConsulta(@RequestParam(value = "criterio", required = false) String criterio,
                                  @RequestParam(value = "valor", required = false) String valor,
                                  Model model) {
        if (valor != null && !valor.isEmpty()) {
            switch (criterio) {
                case "dni" -> model.addAttribute("citas", citaService.buscarPorDni(valor));
                case "nombre" -> model.addAttribute("citas", citaService.buscarPorNombre(valor));
                case "codigo" -> {
                    var c = citaService.buscarPorCodigo(valor);
                    model.addAttribute("citas", c != null ? java.util.List.of(c) : java.util.List.of());
                }
                default -> model.addAttribute("citas", citaService.listarCitas());
            }
        } else {
            model.addAttribute("citas", citaService.listarCitas());
        }
        return "citas/consultar";
    }

    @PostMapping("/anular/{id}")
    public String anularCita(@PathVariable Long id, @RequestParam String razon) {
        citaService.anularCita(id, razon, "recepcionista");
        return "redirect:/citas/consultar";
    }

    private String generarNuevoCodigo(String ultimoCodigo) {
    if (ultimoCodigo == null || ultimoCodigo.isEmpty()) {
        return "C-001";
    }
    try {
        int numero = Integer.parseInt(ultimoCodigo.substring(2));
        numero++;
        return String.format("C-%03d", numero);
    } catch (Exception e) {
        return "C-001";
    }
}
}
