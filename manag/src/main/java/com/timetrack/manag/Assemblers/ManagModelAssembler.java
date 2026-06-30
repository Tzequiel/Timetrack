package com.timetrack.manag.Assemblers;

import com.timetrack.manag.Controller.UsuarioController;
import com.timetrack.manag.Model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ManagModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(UsuarioController.class).verPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).verTodos()).withRel("todos-los-usuarios")
        );
    }
}