package com.timetrack.location.Model;

import jakarta.validation.constraints.NotNull;

public class LocationRequest {

    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long sucursalId;

    @NotNull(message = "La latitud del celular es obligatoria")
    private Double latitudCelular;

    @NotNull(message = "La longitud del celular es obligatoria")
    private Double longitudCelular;

    // --- GETTERS Y SETTERS ---
    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }

    public Double getLatitudCelular() { return latitudCelular; }
    public void setLatitudCelular(Double latitudCelular) { this.latitudCelular = latitudCelular; }

    public Double getLongitudCelular() { return longitudCelular; }
    public void setLongitudCelular(Double longitudCelular) { this.longitudCelular = longitudCelular; }
}