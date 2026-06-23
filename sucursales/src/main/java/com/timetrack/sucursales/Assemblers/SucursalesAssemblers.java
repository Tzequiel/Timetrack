package com.timetrack.sucursales.Assemblers;

import com.timetrack.sucursales.Controller.SucursalController;
import com.timetrack.sucursales.Model.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class SucursalesAssemblers implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {
    @Override
    public EntityModel<Sucursal> toModel(Sucursal entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(SucursalController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(SucursalController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}
