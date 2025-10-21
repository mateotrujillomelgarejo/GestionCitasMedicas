package com.clinica.citas.service;

import java.util.List;

import com.clinica.citas.entity.Auditoria;
import com.clinica.citas.entity.Usuario;

public interface AuditoriaService {
    void registrar(String accion, String tabla, String descripcion);
    List<Auditoria> listarTodas();
    List<Auditoria> listarPorUsuario(Usuario usuario);
}
