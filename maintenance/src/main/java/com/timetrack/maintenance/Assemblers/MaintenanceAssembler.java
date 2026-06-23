package com.timetrack.maintenance.Assemblers;

import com.timetrack.maintenance.Controller.LogAuditoriaController;
import com.timetrack.maintenance.Model.LogAuditoria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class MaintenanceAssembler implements RepresentationModelAssembler<LogAuditoria, EntityModel<LogAuditoria>> {
    @Override
    public EntityModel<LogAuditoria> toModel(LogAuditoria entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(LogAuditoriaController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(LogAuditoriaController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}