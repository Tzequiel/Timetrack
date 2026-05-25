package com.timetrack.metrics.Service;

import com.timetrack.metrics.Model.AusenciaDiariaDTO;
import com.timetrack.metrics.Model.ExportRequestDTO;
import com.timetrack.metrics.Model.ReporteAsistenciaDTO;
import com.timetrack.metrics.Model.ReporteExportado;
import com.timetrack.metrics.Repository.ReporteExportadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class MetricsService {

    @Autowired
    private ReporteExportadoRepository exportadoRepository;

    public ReporteAsistenciaDTO generarResumenMensual(Long usuarioId) {
        // En producción, aquí harías un FeignClient a 'attendance' para contar marcas
        int totalAsistenciasSimuladas = 22;
        String mensaje = "Resumen consolidado: El empleado completó el 95% de sus jornadas obligatorias.";

        return new ReporteAsistenciaDTO(usuarioId, totalAsistenciasSimuladas, mensaje);
    }


    public List<AusenciaDiariaDTO> obtenerAusenciasDelDia() {
        return Arrays.asList(
                new AusenciaDiariaDTO(3L, "Roberto Gomez", LocalDate.now(), "Inasistencia - Sin marcaje de entrada registrado"),
                new AusenciaDiariaDTO(7L, "Elena Villagrán", LocalDate.now(), "Inasistencia - Sin justificación médica")
        );
    }

    public ReporteExportado registrarYExportar(ExportRequestDTO dto) {
        if (!Arrays.asList("PDF", "EXCEL", "CSV").contains(dto.getTipoReporte().toUpperCase())) {
            throw new IllegalArgumentException("Formato de exportación no soportado.");
        }

        ReporteExportado logExportacion = new ReporteExportado(
                dto.getTipoReporte().toUpperCase(),
                dto.getPeriodo(),
                LocalDateTime.now(),
                "COMPLETADO"
        );

        return exportadoRepository.save(logExportacion);
    }
}