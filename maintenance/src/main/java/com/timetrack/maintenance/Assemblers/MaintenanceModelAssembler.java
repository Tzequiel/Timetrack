package com.timetrack.maintenance.Assemblers;

import com.timetrack.maintenance.Controller.LogAuditoriaController;
import com.timetrack.maintenance.Model.LogAuditoria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MaintenanceModelAssembler implements RepresentationModelAssembler<LogAuditoria, EntityModel<LogAuditoria>> {

    @Override
    public EntityModel<LogAuditoria> toModel(LogAuditoria entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(LogAuditoriaController.class).verPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(LogAuditoriaController.class).verLogs()).withRel("todos-los-logs")
        );
    }
}