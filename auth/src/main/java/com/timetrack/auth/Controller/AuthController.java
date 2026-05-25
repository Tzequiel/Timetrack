package com.timetrack.auth.Controller;

import com.timetrack.auth.Model.LoginRequest;
import com.timetrack.auth.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.validarLogin(request);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Credenciales incorrectas");
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String tokenLimpio = token.replace("Bearer ", "");
        String resultado = authService.logout(tokenLimpio);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        String tokenLimpio = token.replace("Bearer ", "");
        boolean isValid = authService.validateToken(tokenLimpio);
        if (isValid) {
            return ResponseEntity.ok("Token válido");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
    }
}