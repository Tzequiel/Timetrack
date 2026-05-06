package com.timetrack.biometric.Model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BIOMETRIA")
public class Biometria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "vector_facial")
    private String vectorFacial; 
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
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