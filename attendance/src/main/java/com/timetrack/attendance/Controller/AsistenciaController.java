package com.timetrack.attendance.Controller;

import com.timetrack.attendance.Assemblers.AttendanceModelAssembler;
import com.timetrack.attendance.Model.Asistencia;
import com.timetrack.attendance.Service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/attendance")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    // Inyectamos nuestro Assembler recién configurado
    @Autowired
    private AttendanceModelAssembler assembler;

    @PostMapping("/clock-in")
    public ResponseEntity<?> clockIn(@RequestBody Asistencia asistencia) {
        try {
            Asistencia resultado = asistenciaService.registrarMarcaje(asistencia, 1L);
            // Empacamos el resultado con el Assembler
            return ResponseEntity.ok(assembler.toModel(resultado));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Marcaje rechazado")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            throw e;
        }
    }

    @PostMapping("/clock-out")
    public ResponseEntity<EntityModel<Asistencia>> clockOut(@RequestBody Asistencia asistencia) {
        Asistencia resultado = asistenciaService.registrarMarcaje(asistencia, 2L);
        return ResponseEntity.ok(assembler.toModel(resultado));
    }

    @PostMapping("/break-start")
    public ResponseEntity<EntityModel<Asistencia>> breakStart(@RequestBody Asistencia asistencia) {
        Asistencia resultado = asistenciaService.registrarMarcaje(asistencia, 3L);
        return ResponseEntity.ok(assembler.toModel(resultado));
    }

    @PostMapping("/break-end")
    public ResponseEntity<EntityModel<Asistencia>> breakEnd(@RequestBody Asistencia asistencia) {
        Asistencia resultado = asistenciaService.registrarMarcaje(asistencia, 4L);
        return ResponseEntity.ok(assembler.toModel(resultado));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<CollectionModel<EntityModel<Asistencia>>> getHistoryByUserId(@PathVariable Long userId) {
        List<EntityModel<Asistencia>> historial = asistenciaService.obtenerMarcajesPorEmpleado(userId)
                .stream()
                .map(assembler::toModel) // Convertimos cada asistencia en un modelo con enlaces
                .collect(Collectors.toList());

        // Devolvemos la colección completa, añadiendo un enlace a esta misma búsqueda
        return ResponseEntity.ok(CollectionModel.of(historial,
                linkTo(methodOn(AsistenciaController.class).getHistoryByUserId(userId)).withSelfRel()));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Asistencia>>> verTodos() {
        List<EntityModel<Asistencia>> asistencias = asistenciaService.obtenerTodosLosMarcajes()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(asistencias,
                linkTo(methodOn(AsistenciaController.class).verTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Asistencia>> verPorId(@PathVariable Long id) {
        Asistencia asistencia = asistenciaService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(asistencia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Asistencia>> actualizar(@PathVariable Long id, @RequestBody Asistencia detalles) {
        Asistencia actualizada = asistenciaService.actualizar(id, detalles);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        asistenciaService.eliminar(id);
        // El delete no necesita devolver el modelo, un mensaje de éxito está perfecto
        return ResponseEntity.ok("Marcaje de asistencia eliminado correctamente");
    }
}