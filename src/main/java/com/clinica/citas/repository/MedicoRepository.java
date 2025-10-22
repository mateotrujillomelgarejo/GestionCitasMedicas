package com.clinica.citas.repository;

import com.clinica.citas.entity.Medico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByNombreContainingIgnoreCaseOrEspecialidadContainingIgnoreCase(String nombre, String especialidad);
    
    @Procedure(procedureName = "listar_medicos_disponibles_por_horario")
    List<Object[]> listarDisponiblesPorHorario(
            @Param("dia_consulta") String diaConsulta,
            @Param("hora_consulta") String horaConsulta
    );
}
