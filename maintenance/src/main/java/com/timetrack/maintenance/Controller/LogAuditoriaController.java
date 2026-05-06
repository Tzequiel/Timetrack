package com.timetrack.maintenance.Controller;
import com.timetrack.maintenance.Model.LogAuditoria;
import com.timetrack.maintenance.Service.LogAuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class LogAuditoriaController {
    @Autowired
    private LogAuditoriaService logService;

    @PostMapping("/event")
    public LogAuditoria registrar(@RequestBody LogAuditoria log) {
        return logService.registrarEvento(log);
    }

    @GetMapping("/logs")
    public List<LogAuditoria> verLogs() {
        return logService.obtenerTodos();
    }
}
