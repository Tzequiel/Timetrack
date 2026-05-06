package com.timetrack.metrics.Model;

public class ReporteAsistenciaDTO {
    private Long usuarioId;
    private int totalAsistencias;
    private String mensaje;

    public ReporteAsistenciaDTO(Long usuarioId, int totalAsistencias, String mensaje) {
        this.usuarioId = usuarioId;
        this.totalAsistencias = totalAsistencias;
        this.mensaje = mensaje;
    }

    // ¡Genera Getters y Setters en IntelliJ!
}