package com.timetrack.auth.Controller;

import com.timetrack.auth.Assemblers.AuthModelAssembler;
import com.timetrack.auth.Model.LoginRequest;
import com.timetrack.auth.Model.UsuarioAuth;
import com.timetrack.auth.Service.AuthService;
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
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Inyectamos el Assembler
    @Autowired
    private AuthModelAssembler assembler;

    // --- Endpoints de Login/Auth (No usan Assembler porque devuelven Strings/Tokens) ---

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

    // --- Endpoints de Gestión de Usuarios (SÍ usan Assembler) ---

    @PostMapping("/users")
    public ResponseEntity<EntityModel<UsuarioAuth>> crearUsuario(@RequestBody UsuarioAuth usuario) {
        UsuarioAuth creado = authService.registrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }

    @GetMapping("/users")
    public ResponseEntity<CollectionModel<EntityModel<UsuarioAuth>>> verTodos() {
        List<EntityModel<UsuarioAuth>> usuarios = authService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(usuarios,
                linkTo(methodOn(AuthController.class).verTodos()).withSelfRel()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<UsuarioAuth>> verPorId(@PathVariable Long id) {
        UsuarioAuth usuario = authService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<EntityModel<UsuarioAuth>> actualizar(@PathVariable Long id, @RequestBody UsuarioAuth detalles) {
        UsuarioAuth actualizado = authService.actualizar(id, detalles);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        authService.eliminar(id);
        return ResponseEntity.ok("Usuario de autenticación eliminado correctamente");
    }
}