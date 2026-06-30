package com.timetrack.maintenance.Controller;

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

import com.timetrack.maintenance.Model.LogAuditoria;
import com.timetrack.maintenance.Service.LogAuditoriaService;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(LogAuditoriaController.class)
public class LogAuditoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogAuditoriaService logService;

    @Test
    @DisplayName("POST /api/audit/event -> Exito")
    public void registrar_Exito() throws Exception {
        var logMock = new LogAuditoria();
        logMock.setId(1L);
        logMock.setAccion("LOGIN_EXITOSO");

        when(logService.registrarEvento(any(LogAuditoria.class))).thenReturn(logMock);

        mockMvc.perform(post("/api/audit/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\": 10, \"accion\": \"LOGIN_EXITOSO\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accion").value("LOGIN_EXITOSO"));
    }

    @Test
    @DisplayName("POST /api/audit/event -> Error")
    public void registrar_Error() throws Exception {
        mockMvc.perform(post("/api/audit/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/audit/logs -> Exito")
    public void verLogs_Exito() throws Exception {
        var logMock = new LogAuditoria();
        logMock.setId(1L);

        when(logService.obtenerTodos()).thenReturn(List.of(logMock));

        mockMvc.perform(get("/api/audit/logs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/audit/logs -> Error (No Content)")
    public void verLogs_Error() throws Exception {
        when(logService.obtenerTodos()).thenReturn(List.of());

        mockMvc.perform(get("/api/audit/logs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/audit/logs/{id} -> Exito")
    public void verPorId_Exito() throws Exception {
        var logMock = new LogAuditoria();
        logMock.setId(5L);

        when(logService.buscarPorId(5L)).thenReturn(Optional.of(logMock));

        mockMvc.perform(get("/api/audit/logs/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @DisplayName("GET /api/audit/logs/{id} -> Error")
    public void verPorId_Error() throws Exception {
        when(logService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/audit/logs/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/audit/logs/{id} -> Exito")
    public void actualizarLog_Exito() throws Exception {
        var logActualizado = new LogAuditoria();
        logActualizado.setId(5L);
        logActualizado.setAccion("LOGOUT");

        when(logService.actualizar(eq(5L), any(LogAuditoria.class))).thenReturn(logActualizado);

        mockMvc.perform(put("/api/audit/logs/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accion\": \"LOGOUT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accion").value("LOGOUT"));
    }

    @Test
    @DisplayName("PUT /api/audit/logs/{id} -> Error")
    public void actualizarLog_Error() throws Exception {
        when(logService.actualizar(eq(99L), any(LogAuditoria.class)))
                .thenThrow(new RuntimeException("Log de auditoría no encontrado con el ID: 99"));

        mockMvc.perform(put("/api/audit/logs/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accion\": \"LOGOUT\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Log de auditoría no encontrado con el ID: 99"));
    }

    @Test
    @DisplayName("DELETE /api/audit/logs/{id} -> Exito")
    public void eliminarLog_Exito() throws Exception {
        doNothing().when(logService).eliminar(5L);

        mockMvc.perform(delete("/api/audit/logs/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Log de auditoría eliminado correctamente."));
    }

    @Test
    @DisplayName("DELETE /api/audit/logs/{id} -> Error")
    public void eliminarLog_Error() throws Exception {
        doThrow(new RuntimeException("No se puede eliminar")).when(logService).eliminar(99L);

        mockMvc.perform(delete("/api/audit/logs/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: No se puede eliminar"));
    }
}