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
}