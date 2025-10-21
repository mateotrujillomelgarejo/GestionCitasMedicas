package com.clinica.citas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String accion; // INSERT, UPDATE, DELETE, LOGIN

    private String tabla;  // Nombre de la tabla afectada

    @Column(length = 500)
    private String descripcion;

    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
