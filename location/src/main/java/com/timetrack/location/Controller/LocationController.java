package com.timetrack.location.Controller;

import com.timetrack.location.Model.Geocerca;
import com.timetrack.location.Model.LocationRequest;
import com.timetrack.location.Service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/geofence")
    public ResponseEntity<Geocerca> crearOActualizarGeofence(@Valid @RequestBody Geocerca geocerca) {
        Geocerca resultado = locationService.guardarOActualizarGeofence(geocerca);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validar(@Valid @RequestBody LocationRequest request) {
        String resultado = locationService.validarUbicacion(request);

        if (resultado.startsWith("Error:")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
        }
        if (resultado.contains("Rechazada")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/geofence")
    public ResponseEntity<List<Geocerca>> obtenerTodasLasGeocercas() {
        List<Geocerca> lista = locationService.obtenerTodas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/geofence/{id}")
    public ResponseEntity<?> obtenerGeocercaPorId(@PathVariable Long id) {
        try {
            Geocerca geocerca = locationService.obtenerPorId(id);
            return ResponseEntity.ok(geocerca);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/geofence/{id}")
    public ResponseEntity<?> actualizarGeocerca(@PathVariable Long id, @Valid @RequestBody Geocerca geocercaDetalles) {
        try {
            Geocerca actualizada = locationService.actualizarGeocerca(id, geocercaDetalles);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/geofence/{id}")
    public ResponseEntity<String> eliminarGeocerca(@PathVariable Long id) {
        try {
            locationService.eliminarGeocerca(id);
            return ResponseEntity.ok("Geocerca con ID " + id + " eliminada correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}