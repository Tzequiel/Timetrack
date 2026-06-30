package com.timetrack.metrics.Controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.timetrack.metrics.Model.AusenciaDiariaDTO;
import com.timetrack.metrics.Model.ExportRequestDTO;
import com.timetrack.metrics.Model.ReporteAsistenciaDTO;
import com.timetrack.metrics.Model.ReporteExportado;
import com.timetrack.metrics.Service.MetricsService;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(MetricsController.class)
public class MetricsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricsService metricsService;

    @Test
    @DisplayName("GET /api/reports/monthly-summary/{userId} -> Exito")
    public void obtenerResumenMensual_Exito() throws Exception {
        var resumenMock = new ReporteAsistenciaDTO(1L, 22, "Resumen consolidado");

        when(metricsService.generarResumenMensual(1L)).thenReturn(resumenMock);

        mockMvc.perform(get("/api/reports/monthly-summary/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.totalAsistencias").value(22));
    }

    @Test
    @DisplayName("GET /api/reports/monthly-summary/{userId} -> Error")
    public void obtenerResumenMensual_Error() throws Exception {
        mockMvc.perform(get("/api/reports/monthly-summary/texto_invalido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/reports/daily-absences -> Exito")
    public void obtenerAusenciasDiarias_Exito() throws Exception {
        var ausenciaMock = new AusenciaDiariaDTO(3L, "Roberto Gomez", LocalDate.now(), "Inasistencia");

        when(metricsService.obtenerAusenciasDelDia()).thenReturn(List.of(ausenciaMock));

        mockMvc.perform(get("/api/reports/daily-absences")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empleadoId").value(3))
                .andExpect(jsonPath("$[0].nombreEmpleado").value("Roberto Gomez"));
    }

    @Test
    @DisplayName("GET /api/reports/daily-absences -> Error (No Content)")
    public void obtenerAusenciasDiarias_Error() throws Exception {
        when(metricsService.obtenerAusenciasDelDia()).thenReturn(List.of());

        mockMvc.perform(get("/api/reports/daily-absences")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("POST /api/reports/export -> Exito")
    public void exportarReporte_Exito() throws Exception {
        var reporteMock = new ReporteExportado();
        reporteMock.setId(10L);
        reporteMock.setTipoReporte("PDF");

        when(metricsService.registrarYExportar(any(ExportRequestDTO.class))).thenReturn(reporteMock);

        mockMvc.perform(post("/api/reports/export")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tipoReporte\": \"PDF\", \"periodo\": \"Mayo 2026\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.tipoReporte").value("PDF"));
    }

    @Test
    @DisplayName("POST /api/reports/export -> Error")
    public void exportarReporte_Error() throws Exception {
        when(metricsService.registrarYExportar(any(ExportRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Formato de exportación no soportado."));

        mockMvc.perform(post("/api/reports/export")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tipoReporte\": \"TXT\", \"periodo\": \"Mayo 2026\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error en exportación: Formato de exportación no soportado."));
    }

    @Test
    @DisplayName("GET /api/reports/export -> Exito")
    public void verHistorialExportaciones_Exito() throws Exception {
        var reporteMock = new ReporteExportado();
        reporteMock.setId(1L);

        when(metricsService.listarExportaciones()).thenReturn(List.of(reporteMock));

        mockMvc.perform(get("/api/reports/export")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/reports/export -> Error (No Content)")
    public void verHistorialExportaciones_Error() throws Exception {
        when(metricsService.listarExportaciones()).thenReturn(List.of());

        mockMvc.perform(get("/api/reports/export")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/reports/export/{id} -> Exito")
    public void verExportacionPorId_Exito() throws Exception {
        var reporteMock = new ReporteExportado();
        reporteMock.setId(5L);

        when(metricsService.buscarExportacionPorId(5L)).thenReturn(Optional.of(reporteMock));

        mockMvc.perform(get("/api/reports/export/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @DisplayName("GET /api/reports/export/{id} -> Error")
    public void verExportacionPorId_Error() throws Exception {
        when(metricsService.buscarExportacionPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/reports/export/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/reports/export/{id} -> Exito")
    public void actualizarRegistroExportacion_Exito() throws Exception {
        var reporteActualizado = new ReporteExportado();
        reporteActualizado.setId(5L);
        reporteActualizado.setEstado("ERROR");

        when(metricsService.actualizarExportacion(eq(5L), any(ReporteExportado.class))).thenReturn(reporteActualizado);

        mockMvc.perform(put("/api/reports/export/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"estado\": \"ERROR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ERROR"));
    }

    @Test
    @DisplayName("PUT /api/reports/export/{id} -> Error")
    public void actualizarRegistroExportacion_Error() throws Exception {
        when(metricsService.actualizarExportacion(eq(99L), any(ReporteExportado.class)))
                .thenThrow(new RuntimeException("Reporte exportado no encontrado con el ID: 99"));

        mockMvc.perform(put("/api/reports/export/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"estado\": \"ERROR\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Reporte exportado no encontrado con el ID: 99"));
    }

    @Test
    @DisplayName("DELETE /api/reports/export/{id} -> Exito")
    public void eliminarRegistroExportacion_Exito() throws Exception {
        doNothing().when(metricsService).eliminarExportacion(5L);

        mockMvc.perform(delete("/api/reports/export/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Registro de exportación eliminado correctamente."));
    }

    @Test
    @DisplayName("DELETE /api/reports/export/{id} -> Error")
    public void eliminarRegistroExportacion_Error() throws Exception {
        doThrow(new RuntimeException("Registro de reporte no encontrado con el ID: 99")).when(metricsService).eliminarExportacion(99L);

        mockMvc.perform(delete("/api/reports/export/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Registro de reporte no encontrado con el ID: 99"));
    }
}