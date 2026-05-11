package com.timetrack.notif.Service;
import com.timetrack.notif.Model.EmailRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    public String enviarComprobante(EmailRequest request) {
        // Aquí iría el código real de JavaMailSender
        System.out.println("Enviando correo a: " + request.getCorreoDestino());
        System.out.println("Mensaje: " + request.getMensaje());

        return "Correo de respaldo enviado a " + request.getCorreoDestino();
    }
}