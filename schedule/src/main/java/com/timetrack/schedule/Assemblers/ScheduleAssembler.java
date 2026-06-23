package com.timetrack.schedule.Assemblers;

import com.timetrack.schedule.Controller.HorarioTurnoController;
import com.timetrack.schedule.Model.HorarioTurno;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class ScheduleAssembler implements RepresentationModelAssembler<HorarioTurno, EntityModel<HorarioTurno>> {
    @Override
    public EntityModel<HorarioTurno> toModel(HorarioTurno entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(HorarioTurnoController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(HorarioTurnoController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}