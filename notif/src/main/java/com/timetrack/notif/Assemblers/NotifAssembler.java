package com.timetrack.notif.Assemblers;

import com.timetrack.notif.Controller.NotificacionController;
import com.timetrack.notif.Model.NotificacionLog;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class NotifAssembler implements RepresentationModelAssembler<NotificacionLog, EntityModel<NotificacionLog>> {
    @Override
    public EntityModel<NotificacionLog> toModel(NotificacionLog entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(NotificacionController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(NotificacionController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}