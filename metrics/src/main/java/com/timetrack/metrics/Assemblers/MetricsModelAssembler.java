package com.timetrack.metrics.Assemblers;

import com.timetrack.metrics.Controller.MetricsController;
import com.timetrack.metrics.Model.ReporteExportado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MetricsModelAssembler implements RepresentationModelAssembler<ReporteExportado, EntityModel<ReporteExportado>> {

    @Override
    public EntityModel<ReporteExportado> toModel(ReporteExportado entity) {
        return EntityModel.of(entity,
                // Enlace al registro de exportación individual por su ID
                linkTo(methodOn(MetricsController.class).verExportacionPorId(entity.getId())).withSelfRel(),
                // Enlace relacional al historial completo de exportaciones
                linkTo(methodOn(MetricsController.class).verHistorialExportaciones()).withRel("historial-exportaciones")
        );
    }
}