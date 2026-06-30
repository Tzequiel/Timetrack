package com.timetrack.manag.Controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.timetrack.manag.Model.Usuario;
import com.timetrack.manag.Service.UsuarioService;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName("POST /api/users -> Exito")
    public void crearUsuario_Exito() throws Exception {
        var usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setNombre("Carlos");

        when(usuarioService.crear(any(Usuario.class))).thenReturn(usuarioMock);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"rut\": \"19.123.456-7\", \"nombre\": \"Carlos\", \"email\": \"carlos@empresa.cl\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    @DisplayName("POST /api/users -> Error")
    public void crearUsuario_Error() throws Exception {
        when(usuarioService.crear(any(Usuario.class)))
                .thenThrow(new IllegalArgumentException("El correo electrónico ya está registrado."));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"rut\": \"19.123.456-7\", \"nombre\": \"Carlos\", \"email\": \"duplicado@empresa.cl\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error de registro: El correo electrónico ya está registrado."));
    }

    @Test
    @DisplayName("GET /api/users -> Exito")
    public void verTodos_Exito() throws Exception {
        var usuarioMock = new Usuario();
        usuarioMock.setId(1L);

        when(usuarioService.listarTodos()).thenReturn(List.of(usuarioMock));

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/users -> Error (No Content)")
    public void verTodos_Error() throws Exception {
        when(usuarioService.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/users/{userId} -> Exito")
    public void verPorId_Exito() throws Exception {
        var usuarioMock = new Usuario();
        usuarioMock.setId(5L);

        when(usuarioService.buscarPorId(5L)).thenReturn(Optional.of(usuarioMock));

        mockMvc.perform(get("/api/users/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @DisplayName("GET /api/users/{userId} -> Error")
    public void verPorId_Error() throws Exception {
        when(usuarioService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/users/{userId} -> Exito")
    public void actualizarUsuario_Exito() throws Exception {
        var usuarioActualizado = new Usuario();
        usuarioActualizado.setId(5L);
        usuarioActualizado.setNombre("Carlos Editado");

        when(usuarioService.actualizar(eq(5L), any(Usuario.class))).thenReturn(usuarioActualizado);

        mockMvc.perform(put("/api/users/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Carlos Editado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Editado"));
    }

    @Test
    @DisplayName("PUT /api/users/{userId} -> Error")
    public void actualizarUsuario_Error() throws Exception {
        when(usuarioService.actualizar(eq(99L), any(Usuario.class)))
                .thenThrow(new RuntimeException("Usuario no encontrado con el ID: 99"));

        mockMvc.perform(put("/api/users/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Carlos Editado\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Usuario no encontrado con el ID: 99"));
    }

    @Test
    @DisplayName("GET /api/users/role/{roleName} -> Exito")
    public void verPorRol_Exito() throws Exception {
        var usuarioMock = new Usuario();
        usuarioMock.setId(10L);

        when(usuarioService.listarPorNombreRol("admin")).thenReturn(List.of(usuarioMock));

        mockMvc.perform(get("/api/users/role/admin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    @DisplayName("GET /api/users/role/{roleName} -> Error")
    public void verPorRol_Error() throws Exception {
        when(usuarioService.listarPorNombreRol("rol_falso"))
                .thenThrow(new IllegalArgumentException("El rol 'rol_falso' no es válido."));

        mockMvc.perform(get("/api/users/role/rol_falso")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: El rol 'rol_falso' no es válido."));
    }

    @Test
    @DisplayName("DELETE /api/users/{userId} -> Exito")
    public void eliminarUsuario_Exito() throws Exception {
        doNothing().when(usuarioService).eliminar(5L);

        mockMvc.perform(delete("/api/users/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario eliminado correctamente."));
    }

    @Test
    @DisplayName("DELETE /api/users/{userId} -> Error")
    public void eliminarUsuario_Error() throws Exception {
        doThrow(new RuntimeException("Usuario no encontrado con el ID: 99")).when(usuarioService).eliminar(99L);

        mockMvc.perform(delete("/api/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Usuario no encontrado con el ID: 99"));
    }
}