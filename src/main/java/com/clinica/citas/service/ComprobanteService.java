package com.clinica.citas.service;

import com.clinica.citas.entity.Comprobante;
import java.util.List;

public interface ComprobanteService {
    Comprobante generarComprobante(Comprobante comprobante);
    List<Comprobante> listarComprobantes();
}
