package com.clinica.citas.service.impl;

import com.clinica.citas.entity.Cita;
import com.clinica.citas.repository.CitaRepository;
import com.clinica.citas.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    @Override
    public Cita registrarCita(Cita cita) {
        if (cita.getCodigoCita() == null || cita.getCodigoCita().isEmpty()) {
            String ultimoCodigo = obtenerUltimoCodigoCita();
            String nuevoCodigo = generarNuevoCodigo(ultimoCodigo);
            cita.setCodigoCita(nuevoCodigo);
        }
        cita.setFechaRegistro(LocalDate.now());
        cita.setEstado("PENDIENTE");
        return citaRepository.save(cita);
    }   



    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    @Override
    public List<Cita> buscarPorDni(String dni) {
        return citaRepository.findByPaciente_Dni(dni);
    }

    @Override
    public List<Cita> buscarPorNombre(String nombre) {
        return citaRepository.findByPaciente_NombreCompletoContainingIgnoreCase(nombre);
    }

    @Override
    public Cita buscarPorCodigo(String codigo) {
        return citaRepository.findByCodigoCita(codigo);
    }

    @Override
    public void anularCita(Long id, String razon, String usuario) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setEstado("CANCELADA");
        cita.setRazonAnulacion(razon);
        cita.setUsuarioAnulacion(usuario);
        cita.setFechaAnulacion(LocalDate.now());
        citaRepository.save(cita);
    }

    @Override
    public String obtenerUltimoCodigoCita() {
        String ultimoCodigo = citaRepository.obtenerUltimoCodigo();

    return ultimoCodigo;
    }

    private String generarNuevoCodigo(String ultimoCodigo) {
        if (ultimoCodigo == null || ultimoCodigo.isEmpty()) {
        return "C-001";
    }
    int numero = Integer.parseInt(ultimoCodigo.substring(2));
    numero++;
    return String.format("C-%03d", numero);
    }



    @Override
    public List<Cita> buscarPorCriterio(String criterio, String valor) {
        return switch (criterio) {
        case "dni" -> buscarPorDni(valor);
        case "nombre" -> buscarPorNombre(valor);
        case "codigo" -> {
            Cita c = buscarPorCodigo(valor);
            yield (c != null) ? List.of(c) : List.of();
        }
        default -> listarCitas();
    };
    }



    @Override
    public Cita buscarPorId(Long id) {
        return citaRepository.getById(id);
    }
}
