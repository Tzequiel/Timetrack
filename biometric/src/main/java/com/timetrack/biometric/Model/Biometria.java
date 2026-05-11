package com.timetrack.biometric.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "BIOMETRIA")
public class Biometria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El vector facial es obligatorio y no puede estar vacío")
    @Column(name = "vector_facial")
    private String vectorFacial; 
    
    @NotNull(message = "La fecha de registro es obligatoria")
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "USUARIO_id")
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVectorFacial() {
        return vectorFacial;
    }

    public void setVectorFacial(String vectorFacial) {
        this.vectorFacial = vectorFacial;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}