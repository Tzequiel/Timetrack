package com.timetrack.biometric.Controller;
import com.timetrack.biometric.Model.Biometria;
import com.timetrack.biometric.Service.BiometriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/biometrics")
public class BiometriaController {
    @Autowired
    private BiometriaService biometriaService;

    @PostMapping("/register")
    public Biometria registrarRostro(@RequestBody Biometria biometria) {
        return biometriaService.registrar(biometria);
    }

    @GetMapping("/verify/{usuarioId}/{vector}")
    public String verificarIdentidad(@PathVariable Long usuarioId, @PathVariable String vector) {
        boolean match = biometriaService.verificar(usuarioId, vector);
        return match ? "Verificacion Facial Exitosa" : "Fallo en Verificacion Facial";
    }
}