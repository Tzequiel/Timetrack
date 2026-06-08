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

    // 1. GET /api/reports/monthly-summary/{userId}
    @GetMapping("/monthly-summary/{userId}")
    public ResponseEntity<ReporteAsistenciaDTO> obtenerResumenMensual(@PathVariable Long userId) {
        ReporteAsistenciaDTO resumen = metricsService.generarResumenMensual(userId);
        return ResponseEntity.ok(resumen);
    }

    // 2. GET /api/reports/daily-absences
    @GetMapping("/daily-absences")
    public ResponseEntity<List<AusenciaDiariaDTO>> obtenerAusenciasDiarias() {
        List<AusenciaDiariaDTO> ausencias = metricsService.obtenerAusenciasDelDia();
        if (ausencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ausencias);
    }

    // 3. POST /api/reports/export
    @PostMapping("/export")
    public ResponseEntity<?> exportarReporte(@Valid @RequestBody ExportRequestDTO exportRequest) {
        try {
            ReporteExportado transaccion = metricsService.registrarYExportar(exportRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en exportación: " + e.getMessage());
        }
    }
}