package com.clinica.citas.controller;

import com.clinica.citas.entity.Paciente;
import com.clinica.citas.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    // LISTAR
    @GetMapping
    public String listarPacientes(Model model) {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        model.addAttribute("pacientes", pacientes);
        return "pacientes/lista";
    }

    // BUSCAR (fragmento)
    @GetMapping("/buscar")
    public String buscarPacientes(@RequestParam("query") String query, Model model) {
        List<Paciente> pacientes = pacienteService.buscarPacientes(query);
        model.addAttribute("pacientes", pacientes);
        return "fragmentos/listaResultados :: resultadosFragment";
    }

    // BUSCAR para tabla completa
    @GetMapping("/buscarTabla")
    public String buscarPacientesTabla(@RequestParam("query") String query, Model model) {
        List<Paciente> pacientes = pacienteService.buscarPacientes(query);
        model.addAttribute("pacientes", pacientes);
        return "pacientes/lista";
    }

    // FORMULARIO REGISTRAR
    @GetMapping("/registrar")
    public String registrarPacienteForm(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/registrar";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardarPaciente(@ModelAttribute("paciente") Paciente paciente,
                                  RedirectAttributes redirectAttrs) {
        try {
            pacienteService.actualizarPaciente(paciente);
            return "redirect:/pacientes";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al guardar el paciente: " + e.getMessage());
            return "redirect:/pacientes/registrar";
        }
    }

    // FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String editarPacienteForm(@PathVariable("id") Long id, Model model,
                                     RedirectAttributes redirectAttrs) {
        Paciente paciente = pacienteService.obtenerPacientePorId(id);
        if (paciente != null) {
            model.addAttribute("paciente", paciente);
            return "pacientes/editar";
        } else {
            redirectAttrs.addFlashAttribute("error", "Paciente no encontrado");
            return "redirect:/pacientes";
        }
    }

    // ACTUALIZAR
    @PostMapping("/actualizar")
    public String actualizarPaciente(@ModelAttribute("paciente") Paciente paciente,
                                     RedirectAttributes redirectAttrs) {
        try {
            pacienteService.actualizarPaciente(paciente);
            return "redirect:/pacientes";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al actualizar el paciente: " + e.getMessage());
            return "redirect:/pacientes/editar/" + paciente.getIdPaciente();
        }
    }

    // ELIMINAR (o desactivar)
    @GetMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable("id") Long id,
                                   RedirectAttributes redirectAttrs) {
        try {
            pacienteService.eliminarPaciente(id);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al eliminar el paciente: " + e.getMessage());
        }
        return "redirect:/pacientes";
    }
}
