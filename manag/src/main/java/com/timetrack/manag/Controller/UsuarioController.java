package com.timetrack.manag.Controller;

import com.timetrack.manag.Assemblers.ManagModelAssembler;
import com.timetrack.manag.Model.Usuario;
import com.timetrack.manag.Service.UsuarioService;
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
@RequestMapping("/api/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ManagModelAssembler assembler;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.crear(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevoUsuario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de registro: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> verTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Usuario>> usuariosModel = usuarios.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(usuariosModel,
                linkTo(methodOn(UsuarioController.class).verTodos()).withSelfRel()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<EntityModel<Usuario>> verPorId(@PathVariable Long userId) {
        return usuarioService.buscarPorId(userId)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long userId, @Valid @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.actualizar(userId, usuario);
            return ResponseEntity.ok(assembler.toModel(actualizado));
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

            // Mapeamos la lista de usuarios por rol a un CollectionModel
            List<EntityModel<Usuario>> usuariosModel = usuarios.stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(CollectionModel.of(usuariosModel,
                    linkTo(methodOn(UsuarioController.class).verPorRol(roleName)).withSelfRel()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long userId) {
        try {
            usuarioService.eliminar(userId);
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}