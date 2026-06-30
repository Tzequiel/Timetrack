package com.timetrack.notif.Controller;

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

import com.timetrack.notif.Model.EmailRequest;
import com.timetrack.notif.Service.NotificacionService;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService notifService;

    @Test
    @DisplayName("POST /api/notifications/send -> Exito")
    public void enviarEmail_Exito() throws Exception {
        when(notifService.enviarComprobante(any(EmailRequest.class)))
                .thenReturn("Correo de respaldo enviado y registrado para el usuario ID: 1");

        mockMvc.perform(post("/api/notifications/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\": 1, \"correoDestino\": \"test@empresa.cl\", \"asunto\": \"Test\", \"mensaje\": \"Hola\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Correo de respaldo enviado y registrado para el usuario ID: 1"));
    }

    @Test
    @DisplayName("POST /api/notifications/send -> Error")
    public void enviarEmail_Error() throws Exception {
        mockMvc.perform(post("/api/notifications/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/notifications -> Exito")
    public void obtenerTodas_Exito() throws Exception {
        var emailMock = new EmailRequest();
        emailMock.setUsuarioId(1L);

        when(notifService.obtenerTodas()).thenReturn(List.of(emailMock));

        mockMvc.perform(get("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value(1));
    }

    @Test
    @DisplayName("GET /api/notifications -> Error")
    public void obtenerTodas_Error() throws Exception {
        mockMvc.perform(get("/api/notifications")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @DisplayName("GET /api/notifications/{id} -> Exito")
    public void obtenerPorId_Exito() throws Exception {
        var emailMock = new EmailRequest();
        emailMock.setUsuarioId(5L);

        when(notifService.obtenerPorId(5L)).thenReturn(emailMock);

        mockMvc.perform(get("/api/notifications/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(5));
    }

    @Test
    @DisplayName("GET /api/notifications/{id} -> Error")
    public void obtenerPorId_Error() throws Exception {
        when(notifService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/notifications/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/notifications/{id} -> Exito")
    public void actualizarNotificacion_Exito() throws Exception {
        var emailActualizado = new EmailRequest();
        emailActualizado.setAsunto("Asunto Editado");

        when(notifService.actualizar(eq(5L), any(EmailRequest.class))).thenReturn(emailActualizado);

        mockMvc.perform(put("/api/notifications/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"asunto\": \"Asunto Editado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.asunto").value("Asunto Editado"));
    }

    @Test
    @DisplayName("PUT /api/notifications/{id} -> Error")
    public void actualizarNotificacion_Error() throws Exception {
        when(notifService.actualizar(eq(99L), any(EmailRequest.class))).thenReturn(null);

        mockMvc.perform(put("/api/notifications/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"asunto\": \"Asunto Editado\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/notifications/{id} -> Exito")
    public void eliminarNotificacion_Exito() throws Exception {
        when(notifService.eliminar(5L)).thenReturn(true);

        mockMvc.perform(delete("/api/notifications/5"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/notifications/{id} -> Error")
    public void eliminarNotificacion_Error() throws Exception {
        when(notifService.eliminar(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/notifications/99"))
                .andExpect(status().isNotFound());
    }
}