package com.timetrack.attendance.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "ASISTENCIA")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "La fecha y hora de marcaje es obligatoria")
    @Column(name = "fecha_hora_marcaje")
    private LocalDateTime fechaHoraMarcaje;

    @NotNull(message = "La latitud de la marca es obligatoria")
    @Column(name = "latitud_marca")
    private Double latitudMarca;

    @NotNull(message = "La longitud de la marca es obligatoria")
    @Column(name = "longitud_marca")
    private Double longitudMarca;

    @NotBlank(message = "El estado de validación biométrica no puede estar en blanco")
    @Column(name = "validacion_biometrica",length = 255 )
    private String validacionBiometrica;

    @NotBlank(message = "El estado de validación GPS no puede estar en blanco")
    @Column(name = "validacion_gps", length = 255)
    private String validacionGps;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "USUARIO_id")
    private Long usuarioId;

    @NotNull(message = "El ID del tipo de marcaje es obligatorio")
    @Column(name = "TIPO_MARCAJE_id")
    private Long tipoMarcajeId;

    public Asistencia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHoraMarcaje() {
        return fechaHoraMarcaje;
    }

    public void setFechaHoraMarcaje(LocalDateTime fechaHoraMarcaje) {
        this.fechaHoraMarcaje = fechaHoraMarcaje;
    }

    public Double getLatitudMarca() {
        return latitudMarca;
    }

    public void setLatitudMarca(Double latitudMarca) {
        this.latitudMarca = latitudMarca;
    }

    public Double getLongitudMarca() {
        return longitudMarca;
    }

    public void setLongitudMarca(Double longitudMarca) {
        this.longitudMarca = longitudMarca;
    }

    public String getValidacionBiometrica() {
        return validacionBiometrica;
    }

    public void setValidacionBiometrica(String validacionBiometrica) {
        this.validacionBiometrica = validacionBiometrica;
    }

    public String getValidacionGps() {
        return validacionGps;
    }

    public void setValidacionGps(String validacionGps) {
        this.validacionGps = validacionGps;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getTipoMarcajeId() {
        return tipoMarcajeId;
    }

    public void setTipoMarcajeId(Long tipoMarcajeId) {
        this.tipoMarcajeId = tipoMarcajeId;
    }
}