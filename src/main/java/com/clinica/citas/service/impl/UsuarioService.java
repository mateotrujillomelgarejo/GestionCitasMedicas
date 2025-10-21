package com.clinica.citas.service.impl;

import com.clinica.citas.entity.Rol;
import com.clinica.citas.entity.Usuario;
import com.clinica.citas.repository.RolRepository;
import com.clinica.citas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final AuditoriaServiceImpl auditoriaService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .disabled(!u.isActivo())
                .authorities(new SimpleGrantedAuthority("ROLE_" + u.getRol().getNombre()))
                .build();
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getPassword() == null || usuario.getPassword().trim().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActivo(true);
        Usuario guardado = usuarioRepository.save(usuario);
        auditoriaService.registrar("INSERT", "Usuario", "Nuevo usuario: " + usuario.getUsername());
        return guardado;
    }

    public void actualizar(Usuario usuario) {
        Usuario existing = usuarioRepository.findById(usuario.getId()).orElseThrow();
        if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        existing.setUsername(usuario.getUsername());
        existing.setRol(usuario.getRol());
        existing.setActivo(usuario.isActivo());
        usuarioRepository.save(existing);
        auditoriaService.registrar("UPDATE", "Usuario", "Usuario actualizado: " + existing.getUsername());
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
        auditoriaService.registrar("DELETE", "Usuario", "Usuario eliminado ID: " + id);
    }

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public void desactivar(Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow();
        u.setActivo(false);
        usuarioRepository.save(u);
        auditoriaService.registrar("UPDATE", "Usuario", "Usuario desactivado: " + u.getUsername());
    }
}
