package com.timetrack.auth.Service;
import com.timetrack.auth.Model.LoginRequest;
import com.timetrack.auth.Model.UsuarioAuth;
import com.timetrack.auth.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    public String validarLogin(LoginRequest request) {
        UsuarioAuth usuario = authRepository.findByEmail(request.getEmail());
        if (usuario != null && usuario.getPasswordHash().equals(request.getPassword())) {
            return "Login exitoso. Bienvenido a TimeTrack.";
        }
        return "Error: Credenciales incorrectas.";
    }
}