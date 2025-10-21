package com.clinica.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinica.citas.entity.Auditoria;
import com.clinica.citas.entity.Usuario;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long>{
    List<Auditoria> findByUsuario(Usuario usuario);
    List<Auditoria> findByTablaOrderByFechaHoraDesc(String tabla);
}
