package com.timetrack.schedule.Controller;

import com.timetrack.schedule.Model.HorarioTurno;
import com.timetrack.schedule.Service.HorarioTurnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class HorarioTurnoController {

    @Autowired
    private HorarioTurnoService horarioService;

    @PostMapping
    public HorarioTurno crearHorario(@Valid @RequestBody HorarioTurno horario) {
        return horarioService.crear(horario);
    }

    @GetMapping("/user/{userId}")
    public List<HorarioTurno> verTurnosEmpleado(@PathVariable Long userId) {
        return horarioService.buscarPorUsuario(userId);
    }

    @PutMapping("/{scheduleId}")
    public HorarioTurno actualizarHorario(@PathVariable Long scheduleId, @Valid @RequestBody HorarioTurno horario) {
        return horarioService.actualizar(scheduleId, horario);
    }
    @GetMapping
    public List<HorarioTurno> obtenerTodos() {
        return horarioService.obtenerTodos();
    }

    // Obtener un turno específico por su ID
    @GetMapping("/{scheduleId}")
    public HorarioTurno obtenerPorId(@PathVariable Long scheduleId) {
        return horarioService.obtenerPorId(scheduleId);
    }

    // Eliminar un turno por su ID
    @DeleteMapping("/{scheduleId}")
    public void eliminarHorario(@PathVariable Long scheduleId) {
        horarioService.eliminar(scheduleId);
    }
}