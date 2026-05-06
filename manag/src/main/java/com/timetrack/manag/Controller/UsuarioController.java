package com.timetrack.manag.Controller;
import com.timetrack.manag.Model.Usuario;
import com.timetrack.manag.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crear(usuario);
    }

    @GetMapping
    public List<Usuario> verTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Usuario verPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }
}