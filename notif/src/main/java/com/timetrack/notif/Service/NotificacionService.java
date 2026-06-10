package com.timetrack.notif.Service;

import com.timetrack.notif.Model.EmailRequest;
import com.timetrack.notif.Model.NotificacionLog;
import com.timetrack.notif.Repository.NotificacionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionLogRepository logRepository;

    public String enviarComprobante(EmailRequest request) {
        System.out.println("Enviando correo a: " + request.getCorreoDestino());
        System.out.println("Asunto: " + request.getAsunto());
        System.out.println("Mensaje: " + request.getMensaje());

        NotificacionLog log = new NotificacionLog(
                request.getUsuarioId(),
                request.getCorreoDestino(),
                request.getAsunto(),
                request.getMensaje(),
                LocalDateTime.now()
        );
        logRepository.save(log);

        return "Correo de respaldo enviado y registrado para el usuario ID: " + request.getUsuarioId();
    }

    public List<NotificacionLog> obtenerLogsPorUsuario(Long usuarioId) {
        return logRepository.findByUsuarioId(usuarioId);
    }
    public List<EmailRequest> obtenerTodas() {
        return null;
    }

    public EmailRequest obtenerPorId(Long id) {
        return null;
    }

    public EmailRequest actualizar(Long id, EmailRequest request) {

        return null;
    }

    public boolean eliminar(Long id) {
        return false;
    }
}