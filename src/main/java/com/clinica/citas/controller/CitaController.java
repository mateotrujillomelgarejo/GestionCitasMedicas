package com.clinica.citas.controller;

import com.clinica.citas.entity.Cita;
import com.clinica.citas.service.CitaService;
import com.clinica.citas.service.MedicoService;
import com.clinica.citas.service.PacienteService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;

    @GetMapping("/menu")
        public String mostrarMenuCitas() {
        return "citas/menucitas";
    }



    @GetMapping("/registrar")
    public String mostrarRegistrarCita(Model model, 
                                       @ModelAttribute("mensaje") String mensaje) {

        Cita cita = new Cita();

        String ultimoCodigo = citaService.obtenerUltimoCodigoCita();
        String nuevoCodigo = generarNuevoCodigo(ultimoCodigo);
        cita.setCodigoCita(nuevoCodigo);
        cita.setFechaRegistro(LocalDate.now());

        model.addAttribute("cita", cita);
        model.addAttribute("pacientes", pacienteService.listarPacientes());
        model.addAttribute("medicos", medicoService.listarTodos());
        model.addAttribute("citas", citaService.listarCitas());
        model.addAttribute("fechaActual", LocalDate.now());
        model.addAttribute("mensaje", mensaje);

        return "citas/registrar";
    }

    @PostMapping("/registrar")
    public String registrarCita(@ModelAttribute Cita cita, RedirectAttributes redirectAttrs) {
        try {
            citaService.registrarCita(cita);
            redirectAttrs.addFlashAttribute("mensaje", "✅ Cita registrada correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensaje", "⚠️ Ocurrió un error al registrar la cita.");
        }
        return "redirect:/citas/registrar";
    }

    @GetMapping("/consultar")
public String mostrarConsulta(@RequestParam(value = "criterio", required = false) String criterio,
                              @RequestParam(value = "valor", required = false) String valor,
                              Model model) {
    if (valor != null && !valor.isEmpty()) {
        switch (criterio) {
            case "dni" -> model.addAttribute("citas", citaService.buscarPorDni(valor)
                                                                   .stream()
                                                                   .filter(c -> "PENDIENTE".equals(c.getEstado()))
                                                                   .toList());
            case "nombre" -> model.addAttribute("citas", citaService.buscarPorNombre(valor)
                                                                      .stream()
                                                                      .filter(c -> "PENDIENTE".equals(c.getEstado()))
                                                                      .toList());
            case "codigo" -> {
                var c = citaService.buscarPorCodigo(valor);
                model.addAttribute("citas", (c != null && "PENDIENTE".equals(c.getEstado()))
                                            ? java.util.List.of(c)
                                            : java.util.List.of());
            }
            default -> model.addAttribute("citas", citaService.listarCitas()
                                                               .stream()
                                                               .filter(c -> "PENDIENTE".equals(c.getEstado()))
                                                               .toList());
        }
    } else {
        model.addAttribute("citas", citaService.listarCitas()
                                                 .stream()
                                                 .filter(c -> "PENDIENTE".equals(c.getEstado()))
                                                 .toList());
    }
    return "citas/consultar";
}



    @GetMapping("/anular/{id}")
public String mostrarFormularioAnulacion(@PathVariable("id") Long id, Model model) {
    Cita cita = citaService.buscarPorId(id);
    if (cita == null) {
        return "redirect:/citas/consultar";
    }
    model.addAttribute("cita", cita);
    return "citas/anular";
}



    @PostMapping("/anular/confirmar")
    public String confirmarAnulacion(@ModelAttribute("cita") Cita cita,
                                 @RequestParam("motivoAnulacion") String razon,
                                 RedirectAttributes redirectAttrs) {
    try {
        citaService.anularCita(cita.getIdCita(), razon, "recepcionista");
        redirectAttrs.addFlashAttribute("mensaje", "Cita anulada correctamente.");
    } catch (Exception e) {
        redirectAttrs.addFlashAttribute("mensaje", "Error al anular la cita.");
    }
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
