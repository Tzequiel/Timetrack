package com.timetrack.attendance.Dto;

public class LocationValidationDto {
    private Long sucursalId;
    private Double latitudCelular;
    private Double longitudCelular;

    public Long getSucursalId() { return sucursalId; }
    public void setSucursalId(Long sucursalId) { this.sucursalId = sucursalId; }
    public Double getLatitudCelular() { return latitudCelular; }
    public void setLatitudCelular(Double latitudCelular) { this.latitudCelular = latitudCelular; }
    public Double getLongitudCelular() { return longitudCelular; }
    public void setLongitudCelular(Double longitudCelular) { this.longitudCelular = longitudCelular; }
}