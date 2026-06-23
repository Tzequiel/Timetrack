package com.timetrack.metrics.Assemblers;


import com.timetrack.metrics.Controller.MetricsController;
import com.timetrack.metrics.Model.ReporteExportado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


public class MetricsAssembler implements RepresentationModelAssembler<ReporteExportado, EntityModel<ReporteExportado>> {
    @Override
    public EntityModel<ReporteExportado> toModel(ReporteExportado entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(MetricsController.class).getHistoryByUserId(entity.getId())).withSelfRel(),
                linkTo(methodOn(MetricsController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}