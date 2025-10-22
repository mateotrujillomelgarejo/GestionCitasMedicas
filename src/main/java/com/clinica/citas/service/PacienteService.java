package com.clinica.citas.service;

import com.clinica.citas.entity.Paciente;
import java.util.Optional;
import java.util.List;

public interface PacienteService {
    List<Paciente> buscarPacientes(String query);
    Optional<Paciente> buscarPorDni(String dni);
    Paciente registrarPaciente(Paciente paciente);
    List<Paciente> listarPacientes();
    void eliminarPaciente(Long id);
    Paciente obtenerPacientePorId(Long id);
    void actualizarPaciente(Paciente paciente);
}
