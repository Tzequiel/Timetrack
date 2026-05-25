package com.timetrack.auth.Service;

import com.timetrack.auth.Model.LoginRequest;
import com.timetrack.auth.Model.UsuarioAuth;
import com.timetrack.auth.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    public String validarLogin(LoginRequest request) {
        UsuarioAuth usuario = authRepository.findByEmail(request.getEmail());
        if (usuario != null && usuario.getPasswordHash().equals(request.getPassword())) {
            // Simulamos la creación de un Token (En un entorno real aquí generarías un JWT)
            String token = UUID.randomUUID().toString();
            return token;
        }
        return null;
    }

    public String logout(String token) {
        // En una implementación real con JWT, aquí se agregaría el token a una "lista negra"
        // o se eliminaría la sesión en caché (ej. Redis).
        return "Logout exitoso. El token ha sido invalidado.";
    }

    public boolean validateToken(String token) {
        // Aquí iría la lógica real para validar la firma y expiración de un JWT.
        // Por ahora simularemos que es válido siempre que nos envíen un texto que no esté vacío.
        return token != null && !token.trim().isEmpty() && token.length() > 10;
    }
}