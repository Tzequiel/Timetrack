package com.timetrack.attendance.Controller;

import com.timetrack.attendance.Model.Asistencia;
import com.timetrack.attendance.Service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AsistenciaController { // Nota: Mantén el nombre original de tu clase 'AsistenciaController'

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/clock-in")
    public ResponseEntity<?> clockIn(@RequestBody Asistencia asistencia) {
        try {
            Asistencia resultado = asistenciaService.registrarMarcaje(asistencia, 1L);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Marcaje rechazado")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            throw e;
        }
    }

    @PostMapping("/clock-out")
    public ResponseEntity<Asistencia> clockOut(@RequestBody Asistencia asistencia) {
        Asistencia resultado = asistenciaService.registrarMarcaje(asistencia, 2L);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/break-start")
    public ResponseEntity<Asistencia> breakStart(@RequestBody Asistencia asistencia) {
        Asistencia resultado = asistenciaService.registrarMarcaje(asistencia, 3L);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/break-end")
    public ResponseEntity<Asistencia> breakEnd(@RequestBody Asistencia asistencia) {
        Asistencia resultado = asistenciaService.registrarMarcaje(asistencia, 4L);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Asistencia>> getHistoryByUserId(@PathVariable Long userId) {
        List<Asistencia> historial = asistenciaService.obtenerMarcajesPorEmpleado(userId);
        return ResponseEntity.ok(historial);
    }

    @GetMapping
    public ResponseEntity<List<Asistencia>> verTodos() {
        return ResponseEntity.ok(asistenciaService.obtenerTodosLosMarcajes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> verPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asistenciaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asistencia> actualizar(@PathVariable Long id, @RequestBody Asistencia detalles) {
        return ResponseEntity.ok(asistenciaService.actualizar(id, detalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        asistenciaService.eliminar(id);
        return ResponseEntity.ok("Marcaje de asistencia eliminado correctamente");
    }
}