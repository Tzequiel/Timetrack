package com.timetrack.maintenance.Service;
import com.timetrack.maintenance.Model.LogAuditoria;
import com.timetrack.maintenance.Repository.LogAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogAuditoriaService {
    @Autowired
    private LogAuditoriaRepository logRepository;

    public LogAuditoria registrarEvento(LogAuditoria log) {
        log.setFechaHora(LocalDateTime.now());
        return logRepository.save(log);
    }

    public List<LogAuditoria> obtenerTodos() {
        return logRepository.findAll();
    }
}
