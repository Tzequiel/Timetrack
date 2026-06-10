package com.timetrack.manag.Service;

import com.timetrack.manag.Model.Usuario;
import com.timetrack.manag.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }
        if (usuarioRepository.existsByRut(usuario.getRut())) {
            throw new IllegalArgumentException("El RUT ya está registrado.");
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario actualizar(Long id, Usuario datosActualizados) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setRut(datosActualizados.getRut());
            usuario.setNombre(datosActualizados.getNombre());
            usuario.setApellido(datosActualizados.getApellido());
            usuario.setEmail(datosActualizados.getEmail());

            if (datosActualizados.getPasswordHash() != null && !datosActualizados.getPasswordHash().isBlank()) {
                usuario.setPasswordHash(datosActualizados.getPasswordHash());
            }
            usuario.setEmpresaId(datosActualizados.getEmpresaId());
            usuario.setRolId(datosActualizados.getRolId());
            usuario.setSucursalId(datosActualizados.getSucursalId());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con el ID: " + id));
    }

    public List<Usuario> listarPorNombreRol(String roleName) {
        Long idBuscado;
        if ("admin".equalsIgnoreCase(roleName) || "administrador".equalsIgnoreCase(roleName)) {
            idBuscado = 1L;
        } else if ("empleado".equalsIgnoreCase(roleName) || "employee".equalsIgnoreCase(roleName)) {
            idBuscado = 2L;
        } else {
            throw new IllegalArgumentException("El rol '" + roleName + "' no es válido. Intente con 'admin' o 'empleado'.");
        }
        return usuarioRepository.findByRolId(idBuscado);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se pudo eliminar. Usuario no encontrado con el ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}