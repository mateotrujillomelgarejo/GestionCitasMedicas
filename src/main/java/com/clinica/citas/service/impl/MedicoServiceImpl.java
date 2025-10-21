package com.clinica.citas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinica.citas.entity.Medico;
import com.clinica.citas.repository.MedicoRepository;
import com.clinica.citas.service.MedicoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService{

    private final MedicoRepository medicoRepository;

    @Override
    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    @Override
    public Object buscarMedicos(String query) {
        return medicoRepository.findByNombreContainingIgnoreCaseOrEspecialidadContainingIgnoreCase(query, query);
    }
    
}
