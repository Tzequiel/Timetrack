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
                // Enlace a sí mismo usando el ID del registro biométrico
                linkTo(methodOn(BiometriaController.class).verPorId(entity.getId())).withSelfRel(),
                // Enlace general a todos los registros
                linkTo(methodOn(BiometriaController.class).verTodas()).withRel("todas-las-biometrias")
        );
    }
}