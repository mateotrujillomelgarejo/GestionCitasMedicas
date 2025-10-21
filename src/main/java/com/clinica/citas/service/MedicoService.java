package com.clinica.citas.service;

import java.util.List;

import com.clinica.citas.entity.Medico;

public interface MedicoService {
    List<Medico> listarTodos();
    Object buscarMedicos(String query);
}
