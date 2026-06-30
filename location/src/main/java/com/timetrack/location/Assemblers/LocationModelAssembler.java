package com.timetrack.location.Assemblers;

import com.timetrack.location.Controller.LocationController;
import com.timetrack.location.Model.Geocerca;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class LocationModelAssembler implements RepresentationModelAssembler<Geocerca, EntityModel<Geocerca>> {
    @Override
    public EntityModel<Geocerca> toModel(Geocerca entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(LocationController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(LocationController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}