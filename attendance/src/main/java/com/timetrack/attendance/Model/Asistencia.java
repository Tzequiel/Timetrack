package com.timetrack.attendance.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ASISTENCIA")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_hora_marcaje")
    private LocalDateTime fechaHoraMarcaje;

    @Column(name = "latitud_marca")
    private Double latitudMarca;

    @Column(name = "longitud_marca")
    private Double longitudMarca;

    @Column(name = "validacion_biometrica")
    private String validacionBiometrica;

    @Column(name = "validacion_gps")
    private String validacionGps;

    @Column(name = "USUARIO_id")
    private Long usuarioId;

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
