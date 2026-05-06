package com.timetrack.attendance.Controller;

import com.timetrack.attendance.Model.Asistencia;
import com.timetrack.attendance.Service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance") 
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/marcar")
    public Asistencia registrarAsistencia(@RequestBody Asistencia asistencia) {
        return asistenciaService.registrarMarcaje(asistencia);
    }

    @GetMapping("/todas")
    public List<Asistencia> verTodas() {
        return asistenciaService.obtenerTodosLosMarcajes();
    }

    @GetMapping("/empleado/{usuarioId}")
    public List<Asistencia> verPorEmpleado(@PathVariable Long usuarioId) {
        return asistenciaService.obtenerMarcajesPorEmpleado(usuarioId);
    }
}
