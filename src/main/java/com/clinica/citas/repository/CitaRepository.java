package com.clinica.citas.repository;

import com.clinica.citas.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPaciente_Dni(String dni);
    List<Cita> findByPaciente_NombreCompletoContainingIgnoreCase(String nombre);
    Cita findByCodigoCita(String codigoCita);
    
    @Query(value = "SELECT codigo_cita FROM cita ORDER BY id_cita DESC LIMIT 1", nativeQuery = true)
    String obtenerUltimoCodigo();
    List<Cita> findByEstadoNot(String string);
    List<Cita> findByEstadoNotAndPaciente_NombreCompletoContainingIgnoreCase(String string,String filtro);
}
