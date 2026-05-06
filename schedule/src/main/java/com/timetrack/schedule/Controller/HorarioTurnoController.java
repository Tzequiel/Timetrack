package com.timetrack.schedule.Controller;
import com.timetrack.schedule.Model.HorarioTurno;
import com.timetrack.schedule.Service.HorarioTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class HorarioTurnoController {
    @Autowired
    private HorarioTurnoService horarioService;

    @PostMapping
    public HorarioTurno crearHorario(@RequestBody HorarioTurno horario) {
        return horarioService.crear(horario);
    }

    @GetMapping("/user/{userId}")
    public List<HorarioTurno> verTurnosEmpleado(@PathVariable Long userId) {
        return horarioService.buscarPorUsuario(userId);
    }
}