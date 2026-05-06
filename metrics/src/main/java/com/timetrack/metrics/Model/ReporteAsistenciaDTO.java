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