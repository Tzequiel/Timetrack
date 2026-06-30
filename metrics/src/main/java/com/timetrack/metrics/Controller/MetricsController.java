package com.timetrack.metrics.Controller;

import com.timetrack.metrics.Assemblers.MetricsModelAssembler;
import com.timetrack.metrics.Model.AusenciaDiariaDTO;
import com.timetrack.metrics.Model.ExportRequestDTO;
import com.timetrack.metrics.Model.ReporteAsistenciaDTO;
import com.timetrack.metrics.Model.ReporteExportado;
import com.timetrack.metrics.Service.MetricsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/reports")
public class MetricsController {

    @Autowired
    private MetricsService metricsService;

    // Inyectamos el Assembler de métricas
    @Autowired
    private MetricsModelAssembler assembler;

    // --- Endpoints de Datos Operativos y Resúmenes (Devuelven DTOs sin cambios) ---

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

    // --- Endpoints del Historial de Exportación (Usan Assembler) ---

    @GetMapping("/export")
    public ResponseEntity<CollectionModel<EntityModel<ReporteExportado>>> verHistorialExportaciones() {
        List<ReporteExportado> exportaciones = metricsService.listarExportaciones();
        if (exportaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Mapeamos la lista interna a modelos enriquecidos con hipermedios
        List<EntityModel<ReporteExportado>> exportacionesModel = exportaciones.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(exportacionesModel,
                linkTo(methodOn(MetricsController.class).verHistorialExportaciones()).withSelfRel()));
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<EntityModel<ReporteExportado>> verExportacionPorId(@PathVariable Long id) {
        return metricsService.buscarExportacionPorId(id)
                .map(assembler::toModel) // Si existe el registro, se le añaden los enlaces HATEOAS
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/export/{id}")
    public ResponseEntity<?> actualizarRegistroExportacion(@PathVariable Long id, @Valid @RequestBody ReporteExportado reporte) {
        try {
            ReporteExportado actualizado = metricsService.actualizarExportacion(id, reporte);
            return ResponseEntity.ok(assembler.toModel(actualizado)); // Empacamos con enlaces
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