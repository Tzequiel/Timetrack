package com.timetrack.location.Controller;

import com.timetrack.location.Model.Geocerca;
import com.timetrack.location.Model.LocationRequest;
import com.timetrack.location.Service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}