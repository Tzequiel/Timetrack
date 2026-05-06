package com.timetrack.location.Model;

import jakarta.validation.constraints.NotNull;

public class LocationRequest {
    
    @NotNull(message = "La latitud del celular es obligatoria")
    private Double latitudCelular;
    
    @NotNull(message = "La longitud del celular es obligatoria")
    private Double longitudCelular;
    
    @NotNull(message = "La latitud de la sucursal es obligatoria")
    private Double latitudSucursal;
    
    @NotNull(message = "La longitud de la sucursal es obligatoria")
    private Double longitudSucursal;
    
    @NotNull(message = "El radio permitido en metros es obligatorio")
    private Double radioPermitidoMetros;

    public Double getLatitudCelular() {
        return latitudCelular;
    }

    public void setLatitudCelular(Double latitudCelular) {
        this.latitudCelular = latitudCelular;
    }

    public Double getLongitudCelular() {
        return longitudCelular;
    }

    public void setLongitudCelular(Double longitudCelular) {
        this.longitudCelular = longitudCelular;
    }

    public Double getLatitudSucursal() {
        return latitudSucursal;
    }

    public void setLatitudSucursal(Double latitudSucursal) {
        this.latitudSucursal = latitudSucursal;
    }

    public Double getLongitudSucursal() {
        return longitudSucursal;
    }

    public void setLongitudSucursal(Double longitudSucursal) {
        this.longitudSucursal = longitudSucursal;
    }

    public Double getRadioPermitidoMetros() {
        return radioPermitidoMetros;
    }

    public void setRadioPermitidoMetros(Double radioPermitidoMetros) {
        this.radioPermitidoMetros = radioPermitidoMetros;
    }
}