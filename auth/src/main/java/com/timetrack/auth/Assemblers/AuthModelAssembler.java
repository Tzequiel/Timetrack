package com.timetrack.auth.Assemblers;

import com.timetrack.auth.Controller.AuthController;
import com.timetrack.auth.Model.UsuarioAuth;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AuthModelAssembler implements RepresentationModelAssembler<UsuarioAuth, EntityModel<UsuarioAuth>> {

    @Override
    public EntityModel<UsuarioAuth> toModel(UsuarioAuth entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(AuthController.class).verPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(AuthController.class).verTodos()).withRel("todos-los-usuarios")
        );
    }
}