package com.timetrack.notif.Controller;

import com.timetrack.notif.Assemblers.NotifModelAssembler;
import com.timetrack.notif.Model.EmailRequest;
import com.timetrack.notif.Service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/notifications")
public class NotificacionController {

    @Autowired
    private NotificacionService notifService;

    // Inyectamos el Assembler
    @Autowired
    private NotifModelAssembler assembler;

    // Se eliminó el método duplicado public String enviarEmail(...) para evitar conflictos
    @PostMapping("/send")
    public ResponseEntity<String> enviarEmail(@RequestBody EmailRequest request) {
        String respuesta = notifService.enviarComprobante(request);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EmailRequest>>> obtenerTodas() {
        List<EmailRequest> historial = notifService.obtenerTodas();

        if (historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Convertimos la lista a modelos con enlaces HATEOAS
        List<EntityModel<EmailRequest>> historialModel = historial.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(historialModel,
                linkTo(methodOn(NotificacionController.class).obtenerTodas()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EmailRequest>> obtenerPorId(@PathVariable Long id) {
        EmailRequest notificacion = notifService.obtenerPorId(id);
        if (notificacion != null) {
            return ResponseEntity.ok(assembler.toModel(notificacion)); // Empacamos con enlaces
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EmailRequest>> actualizarNotificacion(@PathVariable Long id, @RequestBody EmailRequest request) {
        EmailRequest actualizada = notifService.actualizar(id, request);
        if (actualizada != null) {
            return ResponseEntity.ok(assembler.toModel(actualizada)); // Empacamos con enlaces
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