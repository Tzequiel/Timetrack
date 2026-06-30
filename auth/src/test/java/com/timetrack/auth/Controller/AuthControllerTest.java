package com.timetrack.auth.Controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.timetrack.auth.Model.LoginRequest;
import com.timetrack.auth.Model.UsuarioAuth;
import com.timetrack.auth.Service.AuthService;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(AuthController.class)public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("POST /api/auth/login -> Exito")
    public void login_Exito() throws Exception {
        when(authService.validarLogin(any(LoginRequest.class))).thenReturn("token-uuid-1234");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@empresa.cl\", \"password\": \"secreto123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("token-uuid-1234"));
    }

    @Test
    @DisplayName("POST /api/auth/login -> Error")
    public void login_Error() throws Exception {
        when(authService.validarLogin(any(LoginRequest.class))).thenReturn(null);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@empresa.cl\", \"password\": \"incorrecto\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Error: Credenciales incorrectas"));
    }

    @Test
    @DisplayName("POST /api/auth/logout -> Exito")
    public void logout_Exito() throws Exception {
        when(authService.logout(anyString())).thenReturn("Logout exitoso. El token ha sido invalidado.");

        mockMvc.perform(post("/api/auth/logout")
                .header("Authorization", "Bearer token-valido"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logout exitoso. El token ha sido invalidado."));
    }

    @Test
    @DisplayName("POST /api/auth/logout -> Error")
    public void logout_Error() throws Exception {
        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/validate-token -> Exito")
    public void validateToken_Exito() throws Exception {
        when(authService.validateToken(anyString())).thenReturn(true);

        mockMvc.perform(post("/api/auth/validate-token")
                .header("Authorization", "Bearer token-valido"))
                .andExpect(status().isOk())
                .andExpect(content().string("Token válido"));
    }

    @Test
    @DisplayName("POST /api/auth/validate-token -> Error")
    public void validateToken_Error() throws Exception {
        when(authService.validateToken(anyString())).thenReturn(false);

        mockMvc.perform(post("/api/auth/validate-token")
                .header("Authorization", "Bearer token-invalido"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Token inválido o expirado"));
    }

    @Test
    @DisplayName("POST /api/auth/users -> Exito")
    public void crearUsuario_Exito() throws Exception {
        var usuarioMock = new UsuarioAuth();
        usuarioMock.setId(1L);
        usuarioMock.setEmail("nuevo@empresa.cl");

        when(authService.registrarUsuario(any(UsuarioAuth.class))).thenReturn(usuarioMock);

        mockMvc.perform(post("/api/auth/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"nuevo@empresa.cl\", \"passwordHash\": \"hash123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("nuevo@empresa.cl"));
    }

    @Test
    @DisplayName("POST /api/auth/users -> Error")
    public void crearUsuario_Error() throws Exception {
        mockMvc.perform(post("/api/auth/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/auth/users -> Exito")
    public void verTodos_Exito() throws Exception {
        var usuarioMock = new UsuarioAuth();
        usuarioMock.setId(1L);

        when(authService.obtenerTodos()).thenReturn(List.of(usuarioMock));

        mockMvc.perform(get("/api/auth/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/auth/users -> Error")
    public void verTodos_Error() throws Exception {
        mockMvc.perform(get("/api/auth/users")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @DisplayName("GET /api/auth/users/{id} -> Exito")
    public void verPorId_Exito() throws Exception {
        var usuarioMock = new UsuarioAuth();
        usuarioMock.setId(5L);

        when(authService.obtenerPorId(5L)).thenReturn(usuarioMock);

        mockMvc.perform(get("/api/auth/users/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @DisplayName("GET /api/auth/users/{id} -> Error")
    public void verPorId_Error() throws Exception {
        mockMvc.perform(get("/api/auth/users/texto_invalido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/auth/users/{id} -> Exito")
    public void actualizar_Exito() throws Exception {
        var usuarioActualizado = new UsuarioAuth();
        usuarioActualizado.setId(5L);
        usuarioActualizado.setEmail("editado@empresa.cl");

        when(authService.actualizar(eq(5L), any(UsuarioAuth.class))).thenReturn(usuarioActualizado);

        mockMvc.perform(put("/api/auth/users/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"editado@empresa.cl\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("editado@empresa.cl"));
    }

    @Test
    @DisplayName("PUT /api/auth/users/{id} -> Error")
    public void actualizar_Error() throws Exception {
        mockMvc.perform(put("/api/auth/users/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/auth/users/{id} -> Exito")
    public void eliminar_Exito() throws Exception {
        doNothing().when(authService).eliminar(5L);

        mockMvc.perform(delete("/api/auth/users/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario de autenticación eliminado correctamente"));
    }

    @Test
    @DisplayName("DELETE /api/auth/users/{id} -> Error")
    public void eliminar_Error() throws Exception {
        mockMvc.perform(delete("/api/auth/users/texto_invalido"))
                .andExpect(status().isBadRequest());
    }
}