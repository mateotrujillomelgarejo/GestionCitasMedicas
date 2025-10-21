package com.clinica.citas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long idPaciente;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(name = "historia_clinica")
    private String historiaClinica;

    @Column
    private String celular;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;
}
