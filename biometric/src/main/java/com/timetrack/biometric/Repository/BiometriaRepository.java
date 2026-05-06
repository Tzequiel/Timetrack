package com.timetrack.biometric.Repository;
import com.timetrack.biometric.Model.Biometria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiometriaRepository extends JpaRepository<Biometria, Long> {
    Biometria findByUsuarioId(Long usuarioId);
}