package com.clinica.citas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(readOnly = false)
    public List<Medico> listarDisponiblesPorHorario(String diaConsulta, String horaConsulta) {
        List<Object[]> resultados = medicoRepository.listarDisponiblesPorHorario(diaConsulta, horaConsulta);
        List<Medico> medicos = new ArrayList<>();

        for (Object[] fila : resultados) {
            Medico m = new Medico();
            m.setIdMedico(((Number) fila[0]).longValue());
            m.setNombre((String) fila[1]);
            m.setEspecialidad((String) fila[2]);
            m.setConsultorio((String) fila[3]);
            medicos.add(m);
        }

        return medicos;
    }

}
