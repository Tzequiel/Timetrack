package com.timetrack.manag.Controller;

import com.timetrack.manag.Model.Usuario;
import com.timetrack.manag.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.crear(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de registro: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Usuario> verPorId(@PathVariable Long userId) {
        return usuarioService.buscarPorId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long userId, @Valid @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.actualizar(userId, usuario);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<?> verPorRol(@PathVariable String roleName) {
        try {
            List<Usuario> usuarios = usuarioService.listarPorNombreRol(roleName);
            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}