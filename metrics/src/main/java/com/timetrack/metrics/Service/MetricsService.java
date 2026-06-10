package com.timetrack.metrics.Service;

import com.timetrack.metrics.Client.AttendanceClient;
import com.timetrack.metrics.Model.AsistenciaDto;
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
import java.util.Optional;

@Service
public class MetricsService {

    @Autowired
    private ReporteExportadoRepository exportadoRepository;

    @Autowired
    private AttendanceClient attendanceClient; 

    public ReporteAsistenciaDTO generarResumenMensual(Long usuarioId) {
        int totalMarcasReales = 0;
        String mensaje = "";

        try {
            List<AsistenciaDto> historial = attendanceClient.obtenerHistorialPorUsuario(usuarioId);
            totalMarcasReales = historial.size();
            mensaje = "Resumen consolidado: El empleado tiene un total de " + totalMarcasReales + " registros de asistencia.";
        } catch (Exception e) {
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

    public List<ReporteExportado> listarExportaciones() {
        return exportadoRepository.findAll();
    }

    public Optional<ReporteExportado> buscarExportacionPorId(Long id) {
        return exportadoRepository.findById(id);
    }

    public ReporteExportado actualizarExportacion(Long id, ReporteExportado datosActualizados) {
        return exportadoRepository.findById(id).map(reporte -> {
            reporte.setTipoReporte(datosActualizados.getTipoReporte());
            reporte.setPeriodo(datosActualizados.getPeriodo());
            reporte.setEstado(datosActualizados.getEstado());
            //Mantenemos la fecha original de exportación intacta
            return exportadoRepository.save(reporte);
        }).orElseThrow(() -> new RuntimeException("Reporte exportado no encontrado con el ID: " + id));
    }

    public void eliminarExportacion(Long id) {
        if (!exportadoRepository.existsById(id)) {
            throw new RuntimeException("No se pudo eliminar. Registro de reporte no encontrado con el ID: " + id);
        }
        exportadoRepository.deleteById(id);
    }
}