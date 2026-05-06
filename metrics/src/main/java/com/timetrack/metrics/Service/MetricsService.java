package com.timetrack.metrics.Service;
import com.timetrack.metrics.Model.ReporteAsistenciaDTO;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {


    public ReporteAsistenciaDTO generarResumenMensual(Long usuarioId) {
        int asistenciasSimuladas = 20;
        return new ReporteAsistenciaDTO(usuarioId, asistenciasSimuladas, "Reporte generado correctamente");
    }
}