package com.timetrack.location.Assemblers;

import com.timetrack.location.Controller.LocationController;
import com.timetrack.location.Model.Geocerca;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LocationModelAssembler implements RepresentationModelAssembler<Geocerca, EntityModel<Geocerca>> {

    @Override
    public EntityModel<Geocerca> toModel(Geocerca entity) {
        return EntityModel.of(entity,
                // Enlace a la geocerca específica usando su ID
                linkTo(methodOn(LocationController.class).obtenerGeocercaPorId(entity.getId())).withSelfRel(),
                // Enlace al listado de todas las geocercas
                linkTo(methodOn(LocationController.class).obtenerTodasLasGeocercas()).withRel("todas-las-geocercas")
        );
    }
}