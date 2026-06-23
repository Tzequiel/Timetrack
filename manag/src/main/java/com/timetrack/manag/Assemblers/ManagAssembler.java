package com.timetrack.manag.Assemblers;

import com.timetrack.manag.Controller.UsuarioController;
import com.timetrack.manag.Model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


public class ManagAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {
    @Override
    public EntityModel<Usuario> toModel(Usuario entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(UsuarioController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}