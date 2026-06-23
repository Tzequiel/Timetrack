package com.timetrack.schedule.Controller;

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

import com.timetrack.schedule.Model.HorarioTurno;
import com.timetrack.schedule.Service.HorarioTurnoService;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(HorarioTurnoController.class)
public class HorarioTurnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HorarioTurnoService horarioService;

    @Test
    @DisplayName("POST /api/schedules -> Exito")
    public void crearHorario_Exito() throws Exception {
        var horarioMock = new HorarioTurno();
        horarioMock.setId(1L);

        when(horarioService.crear(any(HorarioTurno.class))).thenReturn(horarioMock);

        mockMvc.perform(post("/api/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"horaEntrada\": \"08:00\", \"horaSalida\": \"17:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/schedules -> Error")
    public void crearHorario_Error() throws Exception {
        mockMvc.perform(post("/api/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/schedules/user/{userId} -> Exito")
    public void verTurnosEmpleado_Exito() throws Exception {
        var horarioMock = new HorarioTurno();
        horarioMock.setId(1L);

        when(horarioService.buscarPorUsuario(5L)).thenReturn(List.of(horarioMock));

        mockMvc.perform(get("/api/schedules/user/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/schedules/user/{userId} -> Error")
    public void verTurnosEmpleado_Error() throws Exception {
        mockMvc.perform(get("/api/schedules/user/texto_invalido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/schedules/{scheduleId} -> Exito")
    public void actualizarHorario_Exito() throws Exception {
        var horarioActualizado = new HorarioTurno();
        horarioActualizado.setId(10L);

        when(horarioService.actualizar(eq(10L), any(HorarioTurno.class))).thenReturn(horarioActualizado);

        mockMvc.perform(put("/api/schedules/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"horaEntrada\": \"09:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    @DisplayName("PUT /api/schedules/{scheduleId} -> Error")
    public void actualizarHorario_Error() throws Exception {
        mockMvc.perform(put("/api/schedules/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/schedules -> Exito")
    public void obtenerTodos_Exito() throws Exception {
        var horarioMock = new HorarioTurno();
        horarioMock.setId(1L);

        when(horarioService.obtenerTodos()).thenReturn(List.of(horarioMock));

        mockMvc.perform(get("/api/schedules")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/schedules -> Error")
    public void obtenerTodos_Error() throws Exception {
        mockMvc.perform(get("/api/schedules")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @DisplayName("GET /api/schedules/{scheduleId} -> Exito")
    public void obtenerPorId_Exito() throws Exception {
        var horarioMock = new HorarioTurno();
        horarioMock.setId(5L);

        when(horarioService.obtenerPorId(5L)).thenReturn(horarioMock);

        mockMvc.perform(get("/api/schedules/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @DisplayName("GET /api/schedules/{scheduleId} -> Error")
    public void obtenerPorId_Error() throws Exception {
        mockMvc.perform(get("/api/schedules/texto_invalido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/schedules/{scheduleId} -> Exito")
    public void eliminarHorario_Exito() throws Exception {
        doNothing().when(horarioService).eliminar(5L);

        mockMvc.perform(delete("/api/schedules/5"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/schedules/{scheduleId} -> Error")
    public void eliminarHorario_Error() throws Exception {
        mockMvc.perform(delete("/api/schedules/texto_invalido"))
                .andExpect(status().isBadRequest());
    }
}