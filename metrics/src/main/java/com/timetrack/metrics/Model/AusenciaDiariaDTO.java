package com.timetrack.metrics.Model;

import java.time.LocalDate;

public class AusenciaDiariaDTO {
    private Long usuarioId;
    private String nombreEmpleado;
    private LocalDate fecha;
    private String estadoAusencia;

    public AusenciaDiariaDTO(Long usuarioId, String nombreEmpleado, LocalDate fecha, String estadoAusencia) {
        this.usuarioId = usuarioId;
        this.nombreEmpleado = nombreEmpleado;
        this.fecha = fecha;
        this.estadoAusencia = estadoAusencia;
    }

    // Getters y Setters
    public Long getUsuarioId() { return usuarioId; }
    public String getNombreEmpleado() { return nombreEmpleado; }
    public LocalDate getFecha() { return fecha; }
    public String getEstadoAusencia() { return estadoAusencia; }
}