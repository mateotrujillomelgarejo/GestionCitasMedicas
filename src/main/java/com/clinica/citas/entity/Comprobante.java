package com.clinica.citas.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "comprobante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Long idComprobante;

    @Column(name = "numero_cdp", unique = true)
    private String numeroCdp;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    @Column(name = "tipo_comprobante", nullable = false)
    private String tipoComprobante; // Boleta o Factura

    @Column(name = "dni_ruc", nullable = false)
    private String dniRuc;

    @Column(name = "persona_razon_social", nullable = false)
    private String personaRazonSocial;

    @Column(name = "medio_pago", nullable = false)
    private String medioPago;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;
}
