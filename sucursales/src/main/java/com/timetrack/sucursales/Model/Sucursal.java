package com.timetrack.sucursales.Model;
import jakarta.persistence.*;

@Entity
@Table(name = "SUCURSAL")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;

    @Column(name = "latitud_centro")
    private Double latitudCentro;

    @Column(name = "longitud_centro")
    private Double longitudCentro;

    @Column(name = "radio_tolerancia_metros")
    private Integer radioToleranciaMetros;

    @Column(name = "EMPRESA_id")
    private Long empresaId;

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