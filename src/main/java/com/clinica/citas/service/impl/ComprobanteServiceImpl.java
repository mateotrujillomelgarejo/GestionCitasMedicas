package com.clinica.citas.service.impl;

import com.clinica.citas.entity.Comprobante;
import com.clinica.citas.repository.ComprobanteRepository;
import com.clinica.citas.service.ComprobanteService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComprobanteServiceImpl implements ComprobanteService {

    private final ComprobanteRepository comprobanteRepository;

    public ComprobanteServiceImpl(ComprobanteRepository comprobanteRepository) {
        this.comprobanteRepository = comprobanteRepository;
    }

    @Override
    public Comprobante generarComprobante(Comprobante comprobante) {
        if ("CANCELADA".equalsIgnoreCase(comprobante.getCita().getEstado())) {
        throw new IllegalArgumentException("No se puede generar un comprobante para una cita cancelada.");
        }

        comprobante.setNumeroCdp("CDP-" + String.format("%06d", (int)(Math.random() * 1000000)));
        comprobante.setFechaRegistro(LocalDate.now());
        return comprobanteRepository.save(comprobante);
    }

    @Override
    public List<Comprobante> listarComprobantes() {
        return comprobanteRepository.findAll();
    }
}
