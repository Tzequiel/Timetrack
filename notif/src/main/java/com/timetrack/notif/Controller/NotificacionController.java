package com.timetrack.notif.Controller;
import com.timetrack.notif.Model.EmailRequest;
import com.timetrack.notif.Service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificacionController {
    @Autowired
    private NotificacionService notifService;

    @PostMapping("/send")
    public ResponseEntity<String> enviarEmail(@RequestBody EmailRequest request) {
        String respuesta = notifService.enviarComprobante(request);
        return ResponseEntity.ok(respuesta);
    }


    @GetMapping
    public ResponseEntity<List<EmailRequest>> obtenerTodas() {
        List<EmailRequest> historial = notifService.obtenerTodas();
        return ResponseEntity.ok(historial);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmailRequest> obtenerPorId(@PathVariable Long id) {
        EmailRequest notificacion = notifService.obtenerPorId(id);
        if (notificacion != null) {
            return ResponseEntity.ok(notificacion);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmailRequest> actualizarNotificacion(@PathVariable Long id, @RequestBody EmailRequest request) {
        EmailRequest actualizada = notifService.actualizar(id, request);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        boolean eliminado = notifService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}