package com.timetrack.attendance.Controller;

import com.timetrack.attendance.Model.Asistencia;
import com.timetrack.attendance.Service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance") // Esta es la ruta principal
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    // 1. Endpoint para MARCAR asistencia (POST)
    // Se prueba en Postman con: POST http://localhost:8080/api/attendance/marcar
    @PostMapping("/marcar")
    public Asistencia registrarAsistencia(@RequestBody Asistencia asistencia) {
        return asistenciaService.registrarMarcaje(asistencia);
    }

    // 2. Endpoint para VER TODAS las asistencias (GET)
    // Se prueba en Postman con: GET http://localhost:8080/api/attendance/todas
    @GetMapping("/todas")
    public List<Asistencia> verTodas() {
        return asistenciaService.obtenerTodosLosMarcajes();
    }

    // 3. Endpoint para VER ASISTENCIAS DE UN SOLO EMPLEADO (GET)
    // Se prueba en Postman con: GET http://localhost:8080/api/attendance/empleado/1
    @GetMapping("/empleado/{usuarioId}")
    public List<Asistencia> verPorEmpleado(@PathVariable Long usuarioId) {
        return asistenciaService.obtenerMarcajesPorEmpleado(usuarioId);
    }
}