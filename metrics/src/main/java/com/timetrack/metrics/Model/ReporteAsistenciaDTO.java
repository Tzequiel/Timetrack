package com.timetrack.metrics.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReporteAsistenciaDTO {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
    
    @Min(value = 0, message = "El total de asistencias no puede ser negativo")
    private int totalAsistencias;
    
    @NotBlank(message = "El mensaje del reporte no puede estar vacío")
    private String mensaje;

    public ReporteAsistenciaDTO(Long usuarioId, int totalAsistencias, String mensaje) {
        this.usuarioId = usuarioId;
        this.totalAsistencias = totalAsistencias;
        this.mensaje = mensaje;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getTotalAsistencias() {
        return totalAsistencias;
    }

    public void setTotalAsistencias(int totalAsistencias) {
        this.totalAsistencias = totalAsistencias;
    }
}