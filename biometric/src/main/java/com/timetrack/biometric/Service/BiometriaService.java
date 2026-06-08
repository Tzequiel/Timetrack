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

    public Biometria registrarRostro(Long usuarioId, String vectorFacial) {
        Biometria biometria = biometriaRepository.findByUsuarioId(usuarioId);
        if (biometria == null) {
            biometria = new Biometria();
            biometria.setUsuarioId(usuarioId);
        }
        biometria.setVectorFacial(vectorFacial);
        biometria.setFechaRegistro(LocalDateTime.now());
        return biometriaRepository.save(biometria);
    }

    public Biometria registrarHuella(Long usuarioId, String huellaDactilar) {
        Biometria biometria = biometriaRepository.findByUsuarioId(usuarioId);
        if (biometria == null) {
            biometria = new Biometria();
            biometria.setUsuarioId(usuarioId);
        }
        biometria.setHuellaDactilar(huellaDactilar);
        biometria.setFechaRegistro(LocalDateTime.now());
        return biometriaRepository.save(biometria);
    }

    public boolean verificarRostro(Long usuarioId, String vectorPostman) {
        Biometria bd = biometriaRepository.findByUsuarioId(usuarioId);
        if (bd != null && bd.getVectorFacial() != null) {
            return bd.getVectorFacial().equals(vectorPostman);
        }
        return false;
    }

    public boolean verificarHuella(Long usuarioId, String huellaPostman) {
        Biometria bd = biometriaRepository.findByUsuarioId(usuarioId);
        if (bd != null && bd.getHuellaDactilar() != null) {
            return bd.getHuellaDactilar().equals(huellaPostman);
        }
        return false;
    }
}