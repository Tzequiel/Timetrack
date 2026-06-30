package com.timetrack.biometric.Controller;

import com.timetrack.biometric.Assemblers.BiometricModelAssembler;
import com.timetrack.biometric.Model.Biometria;
import com.timetrack.biometric.Service.BiometriaService;
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
@RequestMapping("/api/biometrics")
public class BiometriaController {

    @Autowired
    private BiometriaService biometriaService;

    // Inyectamos el Assembler
    @Autowired
    private BiometricModelAssembler assembler;

    // --- Endpoints de Registro (Usan Assembler) ---

    @PostMapping("/register-face")
    public ResponseEntity<EntityModel<Biometria>> registrarRostro(@RequestParam Long usuarioId, @RequestParam String vectorFacial) {
        Biometria resultado = biometriaService.registrarRostro(usuarioId, vectorFacial);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(resultado));
    }

    @PostMapping("/register-fingerprint")
    public ResponseEntity<EntityModel<Biometria>> registrarHuella(@RequestParam Long usuarioId, @RequestParam String huellaDactilar) {
        Biometria resultado = biometriaService.registrarHuella(usuarioId, huellaDactilar);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(resultado));
    }

    // --- Endpoints de Verificación (NO usan Assembler porque devuelven Strings) ---

    @PostMapping("/verify-face")
    public ResponseEntity<String> verificarRostro(@RequestParam Long usuarioId, @RequestParam String vectorFacial) {
        boolean match = biometriaService.verificarRostro(usuarioId, vectorFacial);
        if (match) {
            return ResponseEntity.ok("Verificación Facial Exitosa");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fallo en Verificación Facial");
    }

    @PostMapping("/verify-fingerprint")
    public ResponseEntity<String> verificarHuella(@RequestParam Long usuarioId, @RequestParam String huellaDactilar) {
        boolean match = biometriaService.verificarHuella(usuarioId, huellaDactilar);
        if (match) {
            return ResponseEntity.ok("Verificación de Huella Dactilar Exitosa");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fallo en Verificación de Huella Dactilar");
    }

    // --- Endpoints de Búsqueda y Gestión (Usan Assembler) ---

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Biometria>>> verTodas() {
        List<EntityModel<Biometria>> biometrias = biometriaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(biometrias,
                linkTo(methodOn(BiometriaController.class).verTodas()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Biometria>> verPorId(@PathVariable Long id) {
        Biometria biometria = biometriaService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(biometria));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<EntityModel<Biometria>> verPorUsuarioId(@PathVariable Long usuarioId) {
        Biometria biometria = biometriaService.obtenerPorUsuarioId(usuarioId);
        return ResponseEntity.ok(assembler.toModel(biometria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        biometriaService.eliminar(id);
        return ResponseEntity.ok("Registro biométrico eliminado correctamente");
    }
}