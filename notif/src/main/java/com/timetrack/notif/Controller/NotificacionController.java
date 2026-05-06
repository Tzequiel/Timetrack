package com.timetrack.notif.Controller;
import com.timetrack.notif.Model.EmailRequest;
import com.timetrack.notif.Service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificacionController {
    @Autowired
    private NotificacionService notifService;

    @PostMapping("/send")
    public String enviarEmail(@RequestBody EmailRequest request) {
        return notifService.enviarComprobante(request);
    }
}