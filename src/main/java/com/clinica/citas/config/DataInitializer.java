package com.clinica.citas.config;

import com.clinica.citas.entity.Rol;
import com.clinica.citas.entity.Usuario;
import com.clinica.citas.repository.RolRepository;
import com.clinica.citas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JdbcTemplate jdbcTemplate;

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

            crearProcedimientoBuscarPacientes();
            crearProcedimientoListarMedicosDisponiblesPorHorario();
        };
    }

private void crearProcedimientoBuscarPacientes() {
    try {
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS buscar_pacientes");

        String sql = """
            CREATE PROCEDURE buscar_pacientes(IN query_param VARCHAR(255))
            BEGIN
                IF query_param IS NULL OR TRIM(query_param) = '' THEN
                    SELECT 
                        id_paciente,
                        nombre_completo,
                        dni,
                        direccion,
                        email,
                        genero,
                        celular,
                        fecha_nacimiento,
                        historia_clinica
                    FROM paciente
                    ORDER BY nombre_completo ASC;
                ELSE
                    SELECT 
                        id_paciente,
                        nombre_completo,
                        dni,
                        direccion,
                        email,
                        genero,
                        celular,
                        fecha_nacimiento,
                        historia_clinica
                    FROM paciente
                    WHERE 
                        LOWER(nombre_completo) LIKE CONCAT('%', LOWER(query_param), '%')
                        OR dni LIKE CONCAT('%', query_param, '%')
                        OR celular LIKE CONCAT('%', query_param, '%')
                    ORDER BY nombre_completo ASC;
                END IF;
            END
        """;

        jdbcTemplate.execute(sql);

        System.out.println("Procedimiento 'buscar_pacientes' creado correctamente.");

    } catch (Exception e) {
        System.err.println("Error al crear el procedimiento 'buscar_pacientes':");
        e.printStackTrace();
    }
}


private void crearProcedimientoListarMedicosDisponiblesPorHorario() {
    try {
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS listar_medicos_disponibles_por_horario");
        String sql = """
                CREATE PROCEDURE listar_medicos_disponibles_por_horario(
                    IN dia_consulta VARCHAR(15),
                    IN hora_consulta TIME
                )
                BEGIN
                    -- Seleccionar los médicos disponibles en ese día y hora
                    SELECT 
                        id_medico,
                        nombre,
                        especialidad,
                        consultorio,
                        horario_inicio,
                        horario_fin,
                        dias_disponibles
                    FROM medico
                    WHERE 
                        disponible = TRUE
                        AND LOWER(dias_disponibles) LIKE LOWER(CONCAT('%', dia_consulta, '%'))
                        AND (
                            (horario_inicio <= horario_fin AND hora_consulta BETWEEN horario_inicio AND horario_fin)
                            OR
                            (horario_inicio > horario_fin AND (hora_consulta >= horario_inicio OR hora_consulta <= horario_fin))
                        )
                    ORDER BY nombre ASC;
                END
                """;

        jdbcTemplate.execute(sql);
        System.out.println("Procedimiento 'listar_medicos_disponibles_por_horario' creado correctamente");
    } catch (Exception e) {
        System.err.println("Error creando procedimiento listar_medicos_disponibles_por_horario:");
        e.printStackTrace();
    }
}


}
