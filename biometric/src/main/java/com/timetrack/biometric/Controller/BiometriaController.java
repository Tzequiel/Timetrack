package com.timetrack.biometric.Controller;

import com.timetrack.biometric.Model.Biometria;
import com.timetrack.biometric.Service.BiometriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/biometrics")
public class BiometriaController {

    @Autowired
    private BiometriaService biometriaService;

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
}