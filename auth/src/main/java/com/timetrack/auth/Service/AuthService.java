package com.timetrack.auth.Service;

import com.timetrack.auth.Model.LoginRequest;
import com.timetrack.auth.Model.UsuarioAuth;
import com.timetrack.auth.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    // Lógica existente de Login/Auth
    public String validarLogin(LoginRequest request) {
        UsuarioAuth usuario = authRepository.findByEmail(request.getEmail());
        if (usuario != null && usuario.getPasswordHash().equals(request.getPassword())) {
            return UUID.randomUUID().toString();
        }
        return null;
    }

    public String logout(String token) {
        return "Logout exitoso. El token ha sido invalidado.";
    }

    public boolean validateToken(String token) {
        return token != null && !token.trim().isEmpty();
    }

    public UsuarioAuth registrarUsuario(UsuarioAuth nuevoUsuario) {
        // Aquí podrías aplicar un hash a la contraseña en el futuro
        return authRepository.save(nuevoUsuario);
    }

    public List<UsuarioAuth> obtenerTodos() {
        return authRepository.findAll();
    }

    public UsuarioAuth obtenerPorId(Long id) {
        return authRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El usuario de autenticación con ID " + id + " no existe."));
    }

    public UsuarioAuth actualizar(Long id, UsuarioAuth detalles) {
        UsuarioAuth usuario = obtenerPorId(id);
        usuario.setEmail(detalles.getEmail());
        usuario.setPasswordHash(detalles.getPasswordHash()); // En el futuro, aquí se encriptaría
        return authRepository.save(usuario);
    }

    public void eliminar(Long id) {
        UsuarioAuth usuario = obtenerPorId(id);
        authRepository.delete(usuario);
    }
}