package com.clinica.citas.repository;

import com.clinica.citas.entity.Medico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByNombreContainingIgnoreCaseOrEspecialidadContainingIgnoreCase(String nombre, String especialidad);
}
