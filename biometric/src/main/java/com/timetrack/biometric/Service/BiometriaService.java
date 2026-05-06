package com.timetrack.biometric.Service;
import com.timetrack.biometric.Model.Biometria;
import com.timetrack.biometric.Repository.BiometriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class BiometriaService {
    @Autowired
    private BiometriaRepository biometriaRepository;

    public Biometria registrar(Biometria biometria) {
        biometria.setFechaRegistro(LocalDateTime.now());
        return biometriaRepository.save(biometria);
    }

    public boolean verificar(Long usuarioId, String vectorPostman) {
        Biometria bd = biometriaRepository.findByUsuarioId(usuarioId);
        if(bd != null) {
            return bd.getVectorFacial().equals(vectorPostman);
        }
        return false;
    }
}