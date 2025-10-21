package com.clinica.citas.repository;

import com.clinica.citas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDni(String dni);

    @Query(value = "CALL buscar_pacientes(:query_param)", nativeQuery = true)
    List<Paciente> buscarPorNombreODniOTelefono(@Param("query_param") String query);

}
