package com.timetrack.metrics.Service;

import java.util.List;
import com.timetrack.metrics.Model.AsistenciaDto;
import com.timetrack.metrics.Client.AttendanceClient;
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

@Service
public class MetricsService {

    @Autowired
    private ReporteExportadoRepository exportadoRepository;

    @Autowired
    private AttendanceClient attendanceClient; // Inyectamos el puente

    public ReporteAsistenciaDTO generarResumenMensual(Long usuarioId) {

        int totalMarcasReales = 0;
        String mensaje = "";

        try {
            // 1. Llamamos a attendance para traer el historial real
            List<AsistenciaDto> historial = attendanceClient.obtenerHistorialPorUsuario(usuarioId);

            // 2. Contamos cuántas marcas tiene en total
            totalMarcasReales = historial.size();

            mensaje = "Resumen consolidado: El empleado tiene un total de " + totalMarcasReales + " registros de asistencia.";

        } catch (Exception e) {
            // Si el microservicio attendance está apagado o falla
            mensaje = "Advertencia: No se pudo conectar con el sistema de asistencias para calcular el total real.";
        }

        return new ReporteAsistenciaDTO(usuarioId, totalMarcasReales, mensaje);
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