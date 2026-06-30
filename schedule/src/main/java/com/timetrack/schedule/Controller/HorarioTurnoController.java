package com.timetrack.schedule.Controller;

import com.timetrack.schedule.Assemblers.ScheduleModelAssembler;
import com.timetrack.schedule.Model.HorarioTurno;
import com.timetrack.schedule.Service.HorarioTurnoService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/schedules")
public class HorarioTurnoController {

    @Autowired
    private HorarioTurnoService horarioService;

    @Autowired
    private ScheduleModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<HorarioTurno>> crearHorario(@Valid @RequestBody HorarioTurno horario) {
        HorarioTurno creado = horarioService.crear(horario);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CollectionModel<EntityModel<HorarioTurno>>> verTurnosEmpleado(@PathVariable Long userId) {
        List<EntityModel<HorarioTurno>> turnos = horarioService.buscarPorUsuario(userId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(turnos,
                linkTo(methodOn(HorarioTurnoController.class).verTurnosEmpleado(userId)).withSelfRel()));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HorarioTurno>>> obtenerTodos() {
        List<EntityModel<HorarioTurno>> todos = horarioService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(todos,
                linkTo(methodOn(HorarioTurnoController.class).obtenerTodos()).withSelfRel()));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<EntityModel<HorarioTurno>> obtenerPorId(@PathVariable Long scheduleId) {
        HorarioTurno turno = horarioService.obtenerPorId(scheduleId);
        return ResponseEntity.ok(assembler.toModel(turno));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<EntityModel<HorarioTurno>> actualizarHorario(@PathVariable Long scheduleId, @Valid @RequestBody HorarioTurno horario) {
        HorarioTurno actualizado = horarioService.actualizar(scheduleId, horario);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> eliminarHorario(@PathVariable Long scheduleId) {
        horarioService.eliminar(scheduleId);
        return ResponseEntity.ok("Horario eliminado correctamente");
    }
}