package com.clinica.citas.service;

import com.clinica.citas.entity.Cita;
import java.util.List;

public interface CitaService {
    Cita registrarCita(Cita cita);
    List<Cita> listarCitas();
    List<Cita> buscarPorDni(String dni);
    List<Cita> buscarPorNombre(String nombre);
    Cita buscarPorCodigo(String codigo);
    void anularCita(Long id, String razon, String usuario);
    String obtenerUltimoCodigoCita();
}
