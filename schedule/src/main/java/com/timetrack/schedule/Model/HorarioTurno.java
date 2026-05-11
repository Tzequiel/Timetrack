package com.timetrack.schedule.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "HORARIO_TURNO")
public class HorarioTurno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La hora de entrada es obligatoria")
    @Column(name = "hora_entrada")
    private String horaEntrada;

    @NotBlank(message = "La hora de salida es obligatoria")
    @Column(name = "hora_salida")
    private String horaSalida;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "USUARIO_id")
    private Long usuarioId;

    @NotNull(message = "El ID del día de la semana es obligatorio")
    @Column(name = "DIA_SEMANA_id")
    private Long diaSemanaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getDiaSemanaId() {
        return diaSemanaId;
    }

    public void setDiaSemanaId(Long diaSemanaId) {
        this.diaSemanaId = diaSemanaId;
    }
}