package com.timetrack.notif.Assemblers;

import com.timetrack.notif.Controller.NotificacionController;
import com.timetrack.notif.Model.EmailRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class NotifModelAssembler implements RepresentationModelAssembler<EmailRequest, EntityModel<EmailRequest>> {

    @Override
    public EntityModel<EmailRequest> toModel(EmailRequest entity) {
        return EntityModel.of(entity,
                // Enlace a la notificación específica
                linkTo(methodOn(NotificacionController.class).obtenerPorId(entity.getId())).withSelfRel(),
                // Enlace al listado de todas las notificaciones/correos
                linkTo(methodOn(NotificacionController.class).obtenerTodas()).withRel("todas-las-notificaciones")
        );
    }
}