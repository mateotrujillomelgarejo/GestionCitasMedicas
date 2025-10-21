package com.clinica.citas.service.impl;

import com.clinica.citas.entity.Paciente;
import com.clinica.citas.repository.PacienteRepository;
import com.clinica.citas.service.PacienteService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override
    public Optional<Paciente> buscarPorDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }

    @Override
    public Paciente registrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    @Transactional
    public List<Paciente> buscarPacientes(String query) {
        return pacienteRepository.buscarPorNombreODniOTelefono(query);
    }
}
