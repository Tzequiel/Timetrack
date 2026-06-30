package com.timetrack.schedule.Assemblers;

import com.timetrack.schedule.Controller.HorarioTurnoController;
import com.timetrack.schedule.Model.HorarioTurno;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ScheduleModelAssembler implements RepresentationModelAssembler<HorarioTurno, EntityModel<HorarioTurno>> {

    @Override
    public EntityModel<HorarioTurno> toModel(HorarioTurno entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(HorarioTurnoController.class).obtenerPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(HorarioTurnoController.class).obtenerTodos()).withRel("todos-los-horarios")
        );
    }
}