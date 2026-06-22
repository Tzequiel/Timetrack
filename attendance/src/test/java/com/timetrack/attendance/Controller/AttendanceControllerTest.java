package com.timetrack.attendance.Controller;

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

import com.timetrack.attendance.Model.Asistencia;
import com.timetrack.attendance.Service.AsistenciaService;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(AsistenciaController.class)
public class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AsistenciaService asistenciaService;

    @Test
    @DisplayName("POST /api/attendance/clock-in -> Exito")
    public void clockIn_Exito() throws Exception {
        var asistenciaMock = new Asistencia();
        asistenciaMock.setId(10L);
        asistenciaMock.setValidacionGps("Aprobada");

        when(asistenciaService.registrarMarcaje(any(Asistencia.class), eq(1L))).thenReturn(asistenciaMock);

        mockMvc.perform(post("/api/attendance/clock-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    @DisplayName("POST /api/attendance/clock-in -> Error")
    public void clockIn_Error() throws Exception {
        when(asistenciaService.registrarMarcaje(any(Asistencia.class), eq(1L)))
            .thenThrow(new RuntimeException("Marcaje rechazado por GPS"));

        mockMvc.perform(post("/api/attendance/clock-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\": 1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Marcaje rechazado por GPS"));
    }

    @Test
    @DisplayName("POST /api/attendance/clock-out -> Exito")
    public void clockOut_Exito() throws Exception {
        var asistenciaMock = new Asistencia();
        asistenciaMock.setId(11L);

        when(asistenciaService.registrarMarcaje(any(Asistencia.class), eq(2L))).thenReturn(asistenciaMock);

        mockMvc.perform(post("/api/attendance/clock-out")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11));
    }

    @Test
    @DisplayName("POST /api/attendance/clock-out -> Error")
    public void clockOut_Error() throws Exception {
        mockMvc.perform(post("/api/attendance/clock-out")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/attendance/break-start -> Exito")
    public void breakStart_Exito() throws Exception {
        var asistenciaMock = new Asistencia();
        asistenciaMock.setId(12L);

        when(asistenciaService.registrarMarcaje(any(Asistencia.class), eq(3L))).thenReturn(asistenciaMock);

        mockMvc.perform(post("/api/attendance/break-start")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12));
    }

    @Test
    @DisplayName("POST /api/attendance/break-start -> Error")
    public void breakStart_Error() throws Exception {
        mockMvc.perform(post("/api/attendance/break-start")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/attendance/break-end -> Exito")
    public void breakEnd_Exito() throws Exception {
        var asistenciaMock = new Asistencia();
        asistenciaMock.setId(13L);

        when(asistenciaService.registrarMarcaje(any(Asistencia.class), eq(4L))).thenReturn(asistenciaMock);

        mockMvc.perform(post("/api/attendance/break-end")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(13));
    }

    @Test
    @DisplayName("POST /api/attendance/break-end -> Error")
    public void breakEnd_Error() throws Exception {
        mockMvc.perform(post("/api/attendance/break-end")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/attendance/history/{userId} -> Exito")
    public void getHistoryByUserId_Exito() throws Exception {
        var a1 = new Asistencia(); 
        a1.setId(10L);

        when(asistenciaService.obtenerMarcajesPorEmpleado(1L)).thenReturn(List.of(a1));

        mockMvc.perform(get("/api/attendance/history/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    @DisplayName("GET /api/attendance/history/{userId} -> Error")
    public void getHistoryByUserId_Error() throws Exception {
        mockMvc.perform(get("/api/attendance/history/texto_invalido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/attendance -> Exito")
    public void verTodos_Exito() throws Exception {
        var a1 = new Asistencia(); 
        a1.setId(10L);
        
        when(asistenciaService.obtenerTodosLosMarcajes()).thenReturn(List.of(a1));

        mockMvc.perform(get("/api/attendance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    @DisplayName("GET /api/attendance -> Error")
    public void verTodos_Error() throws Exception {
        mockMvc.perform(get("/api/attendance")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @DisplayName("GET /api/attendance/{id} -> Exito")
    public void verPorId_Exito() throws Exception {
        var asistenciaMock = new Asistencia();
        asistenciaMock.setId(50L);

        when(asistenciaService.obtenerPorId(50L)).thenReturn(asistenciaMock);

        mockMvc.perform(get("/api/attendance/50")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(50));
    }

    @Test
    @DisplayName("GET /api/attendance/{id} -> Error")
    public void verPorId_Error() throws Exception {
        mockMvc.perform(get("/api/attendance/texto_invalido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/attendance/{id} -> Exito")
    public void actualizar_Exito() throws Exception {
        var asistenciaActualizada = new Asistencia();
        asistenciaActualizada.setId(50L);
        asistenciaActualizada.setLatitudMarca(-41.0); 
        
        when(asistenciaService.actualizar(eq(50L), any(Asistencia.class))).thenReturn(asistenciaActualizada);

        mockMvc.perform(put("/api/attendance/50")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"latitudMarca\": -41.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitudMarca").value(-41.0));
    }

    @Test
    @DisplayName("PUT /api/attendance/{id} -> Error")
    public void actualizar_Error() throws Exception {
        mockMvc.perform(put("/api/attendance/50")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/attendance/{id} -> Exito")
    public void eliminar_Exito() throws Exception {
        doNothing().when(asistenciaService).eliminar(50L);

        mockMvc.perform(delete("/api/attendance/50"))
                .andExpect(status().isOk())
                .andExpect(content().string("Marcaje de asistencia eliminado correctamente"));
    }

    @Test
    @DisplayName("DELETE /api/attendance/{id} -> Error")
    public void eliminar_Error() throws Exception {
        mockMvc.perform(delete("/api/attendance/texto_invalido"))
                .andExpect(status().isBadRequest());
    }
}