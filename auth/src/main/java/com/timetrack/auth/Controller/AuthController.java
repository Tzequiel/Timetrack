package com.timetrack.auth.Controller;

import com.timetrack.auth.Model.LoginRequest;
import com.timetrack.auth.Model.UsuarioAuth;
import com.timetrack.auth.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoints existentes de Login/Auth
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

    @PostMapping("/users")
    public ResponseEntity<UsuarioAuth> crearUsuario(@RequestBody UsuarioAuth usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrarUsuario(usuario));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsuarioAuth> > verTodos() {
        return ResponseEntity.ok(authService.obtenerTodos());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UsuarioAuth> verPorId(@PathVariable Long id) {
        return ResponseEntity.ok(authService.obtenerPorId(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UsuarioAuth> actualizar(@PathVariable Long id, @RequestBody UsuarioAuth detalles) {
        return ResponseEntity.ok(authService.actualizar(id, detalles));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        authService.eliminar(id);
        return ResponseEntity.ok("Usuario de autenticación eliminado correctamente");
    }
}