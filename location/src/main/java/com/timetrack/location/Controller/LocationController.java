package com.timetrack.location.Controller;

import com.timetrack.location.Assemblers.LocationModelAssembler;
import com.timetrack.location.Model.Geocerca;
import com.timetrack.location.Model.LocationRequest;
import com.timetrack.location.Service.LocationService;
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
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // Inyectamos el Assembler
    @Autowired
    private LocationModelAssembler assembler;

    // --- Endpoints de Gestión de Geocercas (Usan Assembler) ---

    @PostMapping("/geofence")
    public ResponseEntity<EntityModel<Geocerca>> crearOActualizarGeofence(@Valid @RequestBody Geocerca geocerca) {
        Geocerca resultado = locationService.guardarOActualizarGeofence(geocerca);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(resultado));
    }

    @GetMapping("/geofence")
    public ResponseEntity<CollectionModel<EntityModel<Geocerca>>> obtenerTodasLasGeocercas() {
        List<EntityModel<Geocerca>> lista = locationService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(lista,
                linkTo(methodOn(LocationController.class).obtenerTodasLasGeocercas()).withSelfRel()));
    }

    @GetMapping("/geofence/{id}")
    public ResponseEntity<?> obtenerGeocercaPorId(@PathVariable Long id) {
        try {
            Geocerca geocerca = locationService.obtenerPorId(id);
            return ResponseEntity.ok(assembler.toModel(geocerca)); // Empacamos con enlaces
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/geofence/{id}")
    public ResponseEntity<?> actualizarGeocerca(@PathVariable Long id, @Valid @RequestBody Geocerca geocercaDetalles) {
        try {
            Geocerca actualizada = locationService.actualizarGeocerca(id, geocercaDetalles);
            return ResponseEntity.ok(assembler.toModel(actualizada)); // Empacamos con enlaces
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // --- Endpoints de Validación y Eliminación (NO usan Assembler) ---

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