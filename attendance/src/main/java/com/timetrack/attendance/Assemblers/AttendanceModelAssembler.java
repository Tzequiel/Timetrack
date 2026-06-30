package com.timetrack.attendance.Assemblers;

import com.timetrack.attendance.Controller.AsistenciaController;
import com.timetrack.attendance.Model.Asistencia;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AttendanceModelAssembler implements RepresentationModelAssembler<Asistencia, EntityModel<Asistencia>> {

    @Override
    public EntityModel<Asistencia> toModel(Asistencia entity) {
        return EntityModel.of(entity,
                // Enlace a sí mismo usando el endpoint verPorId
                linkTo(methodOn(AsistenciaController.class).verPorId(entity.getId())).withSelfRel(),
                // Enlace general para ver todas las asistencias
                linkTo(methodOn(AsistenciaController.class).verTodos()).withRel("todas-las-asistencias")
        );
    }
}