package com.clinica.citas.controller;

import com.clinica.citas.entity.Cita;
import com.clinica.citas.entity.Comprobante;
import com.clinica.citas.repository.CitaRepository;
import com.clinica.citas.service.ComprobanteService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comprobantes")
public class ComprobanteController {

    private final ComprobanteService comprobanteService;
    private final CitaRepository citaRepository;

    public ComprobanteController(ComprobanteService comprobanteService, CitaRepository citaRepository) {
        this.comprobanteService = comprobanteService;
        this.citaRepository = citaRepository;
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("comprobante", new Comprobante());
        model.addAttribute("citas", citaRepository.findByEstadoNot("CANCELADA"));
    return "cdp/comprobante_form";
}


    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("comprobante") Comprobante comprobante, Model model) {
        Long idCita = comprobante.getCita().getIdCita();
        var cita = citaRepository.findById(idCita).orElse(null);

        if (cita == null) {
            model.addAttribute("mensaje", "⚠️ La cita seleccionada no existe.");
        } else {
            try {
                comprobante.setCita(cita);
                Comprobante nuevo = comprobanteService.generarComprobante(comprobante);
                model.addAttribute("mensaje", " CDP creado con Nro. " + nuevo.getNumeroCdp());
            } catch (IllegalArgumentException ex) {
                model.addAttribute("mensaje ", ex.getMessage());
            } catch (Exception ex) {
                model.addAttribute("mensaje", " Error inesperado: " + ex.getMessage());
            }
        }
        model.addAttribute("comprobante", new Comprobante());
        model.addAttribute("citas", citaRepository.findByEstadoNot("CANCELADA"));
        return "cdp/comprobante_form";
    }

    @GetMapping("/buscarCita")
public String buscarCita(@RequestParam(value = "filtro", required = false) String filtro, Model model) {
    List<Cita> citas;

    if (filtro != null && !filtro.isEmpty()) {
        citas = citaRepository.findByEstadoNotAndPaciente_NombreCompletoContainingIgnoreCase("CANCELADA", filtro);
    } else {
        citas = citaRepository.findByEstadoNot("CANCELADA");
    }

    model.addAttribute("citas", citas);
    return "cdp/buscar_cita";
}

@GetMapping("/seleccionarCita")
public String seleccionarCita(@RequestParam("id") Long idCita, Model model) {
    var cita = citaRepository.findById(idCita).orElse(null);

    if (cita == null || "CANCELADA".equalsIgnoreCase(cita.getEstado())) {
        model.addAttribute("mensaje", "La cita no es válida o fue cancelada.");
        model.addAttribute("comprobante", new Comprobante());
        model.addAttribute("citas", citaRepository.findByEstadoNot("CANCELADA"));
        return "cdp/comprobante_form";
    }

    Comprobante comprobante = new Comprobante();
    comprobante.setCita(cita);

    model.addAttribute("comprobante", comprobante);
    return "cdp/comprobante_form";
}


    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("comprobantes", comprobanteService.listarComprobantes());
        return "cdp/comprobante_list";
    }
}
