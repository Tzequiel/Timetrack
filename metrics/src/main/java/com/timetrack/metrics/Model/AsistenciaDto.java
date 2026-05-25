package com.timetrack.metrics.Model;

import java.time.LocalDateTime;

public class AsistenciaDto {
    private Long id;
    private LocalDateTime fechaHoraMarcaje;
    private Long usuarioId;

    // Getters y Setters básicos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFechaHoraMarcaje() { return fechaHoraMarcaje; }
    public void setFechaHoraMarcaje(LocalDateTime fechaHoraMarcaje) { this.fechaHoraMarcaje = fechaHoraMarcaje; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}