package com.clinica.citas.service.impl;

import com.clinica.citas.entity.Auditoria;
import com.clinica.citas.entity.Usuario;
import com.clinica.citas.repository.AuditoriaRepository;
import com.clinica.citas.repository.UsuarioRepository;
import com.clinica.citas.service.AuditoriaService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService{

    private final AuditoriaRepository auditoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public void registrar(String accion, String tabla, String descripcion) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) return;

        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);

        Auditoria registro = Auditoria.builder()
                .accion(accion)
                .tabla(tabla)
                .descripcion(descripcion)
                .fechaHora(LocalDateTime.now())
                .usuario(usuario)
                .build();

        auditoriaRepository.save(registro);
    }

    public List<Auditoria> listarTodas() {
        return auditoriaRepository.findAll();
    }

    public List<Auditoria> listarPorUsuario(Usuario usuario) {
        return auditoriaRepository.findByUsuario(usuario);
    }
}
