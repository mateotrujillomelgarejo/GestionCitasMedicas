package com.clinica.citas.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    @Column(name = "codigo_cita", unique = true, nullable = false)
    private String codigoCita;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;

    @Column(name = "hora_cita", nullable = false)
    private LocalTime horaCita;

    @Column(name = "tipo_atencion", nullable = false, length = 100)
    private String tipoAtencion;

    @Column(name = "estado", nullable = false)
    private String estado; // PENDIENTE | CANCELADA

    @Column(name = "razon_anulacion")
    private String razonAnulacion;

    @Column(name = "fecha_anulacion")
    private LocalDate fechaAnulacion;

    @Column(name = "usuario_anulacion")
    private String usuarioAnulacion;
}
