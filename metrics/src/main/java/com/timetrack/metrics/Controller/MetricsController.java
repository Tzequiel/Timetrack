package com.timetrack.metrics.Controller;
import com.timetrack.metrics.Model.ReporteAsistenciaDTO;
import com.timetrack.metrics.Service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class MetricsController {
    @Autowired
    private MetricsService metricsService;

    @GetMapping("/summary/{usuarioId}")
    public ReporteAsistenciaDTO obtenerResumen(@PathVariable Long usuarioId) {
        return metricsService.generarResumenMensual(usuarioId);
    }
}