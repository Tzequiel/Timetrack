package com.timetrack.location.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "GEOCERCA")
public class Geocerca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    @Column(name = "sucursal_id", unique = true)
    private Long sucursalId;

    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;

    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;

    @NotNull(message = "El radio en metros es obligatorio")
    @Column(name = "radio_metros")
    private Double radioMetros;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public Double getRadioMetros() { return radioMetros; }
    public void setRadioMetros(Double radioMetros) { this.radioMetros = radioMetros; }
}