package com.timetrack.maintenance.Controller;

import com.timetrack.maintenance.Model.LogAuditoria;
import com.timetrack.maintenance.Service.LogAuditoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class LogAuditoriaController {

    @Autowired
    private LogAuditoriaService logService;

    @PostMapping("/event")
    public ResponseEntity<LogAuditoria> registrar(@Valid @RequestBody LogAuditoria log) {
        LogAuditoria resultado = logService.registrarEvento(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<LogAuditoria>> verLogs() {
        List<LogAuditoria> logs = logService.obtenerTodos();
        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<LogAuditoria> verPorId(@PathVariable Long id) {
        return logService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/logs/{id}")
    public ResponseEntity<?> actualizarLog(@PathVariable Long id, @Valid @RequestBody LogAuditoria log) {
        try {
            LogAuditoria actualizado = logService.actualizar(id, log);
            return ResponseEntity.ok(actualizado);
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