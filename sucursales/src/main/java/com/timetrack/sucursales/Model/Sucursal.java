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

}