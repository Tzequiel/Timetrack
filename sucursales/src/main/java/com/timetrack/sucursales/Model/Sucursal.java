package com.timetrack.sucursales.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "SUCURSAL")
public class Sucursal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    private String nombre;
    
    @NotBlank(message = "La dirección de la sucursal es obligatoria")
    private String direccion;

    @NotNull(message = "La latitud del centro de la sucursal es obligatoria")
    @Column(name = "latitud_centro")
    private Double latitudCentro;

    @NotNull(message = "La longitud del centro de la sucursal es obligatoria")
    @Column(name = "longitud_centro")
    private Double longitudCentro;

    @NotNull(message = "El radio de tolerancia en metros es obligatorio")
    @Min(value = 0, message = "El radio de tolerancia no puede ser negativo")
    @Column(name = "radio_tolerancia_metros")
    private Integer radioToleranciaMetros;

    @NotNull(message = "El ID de la empresa es obligatorio")
    @Column(name = "EMPRESA_id")
    private Long empresaId;

    public Sucursal() {}

    public Sucursal(Long id, String nombre, Double latitudCentro, String direccion, Double longitudCentro, Integer radioToleranciaMetros, Long empresaId) {
        this.id = id;
        this.nombre = nombre;
        this.latitudCentro = latitudCentro;
        this.direccion = direccion;
        this.longitudCentro = longitudCentro;
        this.radioToleranciaMetros = radioToleranciaMetros;
        this.empresaId = empresaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitudCentro() {
        return latitudCentro;
    }

    public void setLatitudCentro(Double latitudCentro) {
        this.latitudCentro = latitudCentro;
    }

    public Double getLongitudCentro() {
        return longitudCentro;
    }

    public void setLongitudCentro(Double longitudCentro) {
        this.longitudCentro = longitudCentro;
    }

    public Integer getRadioToleranciaMetros() {
        return radioToleranciaMetros;
    }

    public void setRadioToleranciaMetros(Integer radioToleranciaMetros) {
        this.radioToleranciaMetros = radioToleranciaMetros;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}