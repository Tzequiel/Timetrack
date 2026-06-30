package com.timetrack.maintenance.Controller;

import com.timetrack.maintenance.Assemblers.MaintenanceModelAssembler;
import com.timetrack.maintenance.Model.LogAuditoria;
import com.timetrack.maintenance.Service.LogAuditoriaService;
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
@RequestMapping("/api/audit")
public class LogAuditoriaController {

    @Autowired
    private LogAuditoriaService logService;

    // Inyectamos el Assembler
    @Autowired
    private MaintenanceModelAssembler assembler;

    @PostMapping("/event")
    public ResponseEntity<EntityModel<LogAuditoria>> registrar(@Valid @RequestBody LogAuditoria log) {
        LogAuditoria resultado = logService.registrarEvento(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(resultado));
    }

    @GetMapping("/logs")
    public ResponseEntity<CollectionModel<EntityModel<LogAuditoria>>> verLogs() {
        List<LogAuditoria> logs = logService.obtenerTodos();
        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Convertimos la lista de logs en una lista de modelos con enlaces
        List<EntityModel<LogAuditoria>> logsModel = logs.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(logsModel,
                linkTo(methodOn(LogAuditoriaController.class).verLogs()).withSelfRel()));
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<EntityModel<LogAuditoria>> verPorId(@PathVariable Long id) {
        return logService.buscarPorId(id)
                .map(assembler::toModel) // Pasamos la entidad por el Assembler si existe
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/logs/{id}")
    public ResponseEntity<?> actualizarLog(@PathVariable Long id, @Valid @RequestBody LogAuditoria log) {
        try {
            LogAuditoria actualizado = logService.actualizar(id, log);
            return ResponseEntity.ok(assembler.toModel(actualizado)); // Empacamos con enlaces
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<String> eliminarLog(@PathVariable Long id) {
        try {
            logService.eliminar(id);
            return ResponseEntity.ok("Log de auditoría eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}