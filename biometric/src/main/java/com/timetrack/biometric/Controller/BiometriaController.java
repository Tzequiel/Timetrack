package com.timetrack.biometric.Controller;

import com.timetrack.biometric.Model.Biometria;
import com.timetrack.biometric.Service.BiometriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/biometrics")
public class BiometriaController { // Cambiado o mantenido según tu estructura, usualmente BiometriaController

    @Autowired
    private BiometriaService biometriaService;

    // Endpoints existentes
    @PostMapping("/register-face")
    public ResponseEntity<Biometria> registrarRostro(@RequestParam Long usuarioId, @RequestParam String vectorFacial) {
        Biometria resultado = biometriaService.registrarRostro(usuarioId, vectorFacial);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @PostMapping("/register-fingerprint")
    public ResponseEntity<Biometria> registrarHuella(@RequestParam Long usuarioId, @RequestParam String huellaDactilar) {
        Biometria resultado = biometriaService.registrarHuella(usuarioId, huellaDactilar);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

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

    @GetMapping
    public ResponseEntity<List<Biometria>> verTodas() {
        return ResponseEntity.ok(biometriaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Biometria> verPorId(@PathVariable Long id) {
        return ResponseEntity.ok(biometriaService.obtenerPorId(id));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<Biometria> verPorUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(biometriaService.obtenerPorUsuarioId(usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        biometriaService.eliminar(id);
        return ResponseEntity.ok("Registro biométrico eliminado correctamente");
    }
}