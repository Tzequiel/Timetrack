package com.timetrack.biometric.Assemblers;

import com.timetrack.biometric.Controller.BiometriaController;
import com.timetrack.biometric.Model.Biometria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class BiometricModelAssembler implements RepresentationModelAssembler<Biometria, EntityModel<Biometria>> {
    @Override
    public EntityModel<Biometria> toModel(Biometria entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(BiometriaController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(BiometriaController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}