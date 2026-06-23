package com.timetrack.auth.Assemblers;

import com.timetrack.auth.Model.LoginRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class AuthAssembler implements RepresentationModelAssembler<LoginRequest, EntityModel<LoginRequest>> {
    @Override
    public EntityModel<LoginRequest> toModel(LoginRequest entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(AuthController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(AuthController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}
