package com.clinica.citas.config;

import com.clinica.citas.entity.Rol;
import com.clinica.citas.entity.Usuario;
import com.clinica.citas.repository.RolRepository;
import com.clinica.citas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            Rol adminRol = rolRepository.findByNombre("ADMINISTRADOR")
                    .orElseGet(() -> rolRepository.save(new Rol(null, "ADMINISTRADOR")));

            Rol recepRol = rolRepository.findByNombre("RECEPCIONISTA")
                    .orElseGet(() -> rolRepository.save(new Rol(null, "RECEPCIONISTA")));

            Rol cajeroRol = rolRepository.findByNombre("CAJERO")
                    .orElseGet(() -> rolRepository.save(new Rol(null, "CAJERO")));

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setActivo(true);
                admin.setRol(adminRol);
                usuarioRepository.save(admin);
                System.out.println("Usuario ADMIN creado (admin / admin123)");
            }

            if (usuarioRepository.findByUsername("recepcionista").isEmpty()) {
                Usuario recep = new Usuario();
                recep.setUsername("recepcionista");
                recep.setPassword(encoder.encode("recep123"));
                recep.setActivo(true);
                recep.setRol(recepRol);
                usuarioRepository.save(recep);
                System.out.println("Usuario RECEPCIONISTA creado (recepcionista / recep123)");
            }

            if (usuarioRepository.findByUsername("cajero").isEmpty()) {
                Usuario cajero = new Usuario();
                cajero.setUsername("cajero");
                cajero.setPassword(encoder.encode("cajero123"));
                cajero.setActivo(true);
                cajero.setRol(cajeroRol);
                usuarioRepository.save(cajero);
                System.out.println("Usuario CAJERO creado (cajero / cajero123)");
            }
        };
    }
}
