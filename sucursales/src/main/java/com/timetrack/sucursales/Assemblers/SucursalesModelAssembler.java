package com.timetrack.sucursales.Assemblers;

import com.timetrack.sucursales.Controller.SucursalController;
import com.timetrack.sucursales.Model.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SucursalesModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal entity) {
        return EntityModel.of(entity,
                // Enlace a la sucursal específica
                linkTo(methodOn(SucursalController.class).obtenerPorId(entity.getId())).withSelfRel(),
                // Enlace al listado de sucursales
                linkTo(methodOn(SucursalController.class).verTodas()).withRel("todas-las-sucursales")
        );
    }
}