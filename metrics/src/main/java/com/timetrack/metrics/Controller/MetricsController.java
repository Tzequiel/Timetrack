package com.timetrack.metrics.Controller;

import com.timetrack.metrics.Model.AusenciaDiariaDTO;
import com.timetrack.metrics.Model.ExportRequestDTO;
import com.timetrack.metrics.Model.ReporteAsistenciaDTO;
import com.timetrack.metrics.Model.ReporteExportado;
import com.timetrack.metrics.Service.MetricsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class MetricsController {

    @Autowired
    private MetricsService metricsService;


    @GetMapping("/monthly-summary/{userId}")
    public ResponseEntity<ReporteAsistenciaDTO> obtenerResumenMensual(@PathVariable Long userId) {
        ReporteAsistenciaDTO resumen = metricsService.generarResumenMensual(userId);
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/daily-absences")
    public ResponseEntity<List<AusenciaDiariaDTO>> obtenerAusenciasDiarias() {
        List<AusenciaDiariaDTO> ausencias = metricsService.obtenerAusenciasDelDia();
        if (ausencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ausencias);
    }


    @PostMapping("/export")
    public ResponseEntity<?> exportarReporte(@Valid @RequestBody ExportRequestDTO exportRequest) {
        try {
            ReporteExportado transaccion = metricsService.registrarYExportar(exportRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en exportación: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    public ResponseEntity<List<ReporteExportado>> verHistorialExportaciones() {
        List<ReporteExportado> exportaciones = metricsService.listarExportaciones();
        if (exportaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exportaciones);
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<ReporteExportado> verExportacionPorId(@PathVariable Long id) {
        return metricsService.buscarExportacionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/export/{id}")
    public ResponseEntity<?> actualizarRegistroExportacion(@PathVariable Long id, @Valid @RequestBody ReporteExportado reporte) {
        try {
            ReporteExportado actualizado = metricsService.actualizarExportacion(id, reporte);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/export/{id}")
    public ResponseEntity<String> eliminarRegistroExportacion(@PathVariable Long id) {
        try {
            metricsService.eliminarExportacion(id);
            return ResponseEntity.ok("Registro de exportación eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}