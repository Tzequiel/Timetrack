package com.timetrack.biometric.Assemblers;

import com.timetrack.biometric.Controller.BiometriaController;
import com.timetrack.biometric.Model.Biometria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BiometricModelAssembler implements RepresentationModelAssembler<Biometria, EntityModel<Biometria>> {

    @Override
    public EntityModel<Biometria> toModel(Biometria entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(BiometriaController.class).verPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(BiometriaController.class).verTodas()).withRel("todas-las-biometrias")
        );
    }
}