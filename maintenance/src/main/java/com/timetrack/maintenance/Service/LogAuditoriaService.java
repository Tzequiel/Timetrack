package com.timetrack.maintenance.Service;
import com.timetrack.maintenance.Model.LogAuditoria;
import com.timetrack.maintenance.Repository.LogAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Optional<LogAuditoria> buscarPorId(Long id) {
        return logRepository.findById(id);
    }

    public LogAuditoria actualizar(Long id, LogAuditoria datosActualizados) {
        return logRepository.findById(id).map(log -> {
            log.setUsuarioId(datosActualizados.getUsuarioId());
            log.setAccion(datosActualizados.getAccion());
            // Mantenemos la fecha original o la actualizamos según requieras. 
            // Aquí la actualizamos al momento exacto de la edición:
            log.setFechaHora(LocalDateTime.now()); 
            return logRepository.save(log);
        }).orElseThrow(() -> new RuntimeException("Log de auditoría no encontrado con el ID: " + id));
    }

    public void eliminar(Long id) {
        if (!logRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Log no encontrado con el ID: " + id);
        }
        logRepository.deleteById(id);
    }
}