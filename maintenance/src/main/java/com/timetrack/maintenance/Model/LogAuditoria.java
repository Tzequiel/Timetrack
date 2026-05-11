package com.timetrack.maintenance.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOG_AUDITORIA")
public class LogAuditoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "USUARIO_id")
    private Long usuarioId;

    @NotBlank(message = "La acción de auditoría no puede estar en blanco")
    private String accion;

    @NotNull(message = "La fecha y hora de la acción son obligatorias")
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}