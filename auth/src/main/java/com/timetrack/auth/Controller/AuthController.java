package com.timetrack.auth.Controller;
import com.timetrack.auth.Model.LoginRequest;
import com.timetrack.auth.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.validarLogin(request);
    }
}