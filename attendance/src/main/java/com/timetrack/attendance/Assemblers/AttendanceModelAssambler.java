package com.timetrack.attendance.Assemblers;

import com.timetrack.attendance.Controller.AsistenciaController;
import com.timetrack.attendance.Model.Asistencia;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


public class AttendanceModelAssambler implements RepresentationModelAssembler<Asistencia, EntityModel<Asistencia>> {
    @Override
    public EntityModel<Asistencia> toModel(Asistencia entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(AsistenciaController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(AsistenciaController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}
