package com.timetrack.biometric.Service;

import com.timetrack.biometric.Model.Biometria;
import com.timetrack.biometric.Repository.BiometriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BiometriaService {

    @Autowired
    private BiometriaRepository biometriaRepository;

    // Métodos existentes de registro y verificación
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

    public List<Biometria> obtenerTodas() {
        return biometriaRepository.findAll();
    }

    public Biometria obtenerPorId(Long id) {
        return biometriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El registro biométrico con ID " + id + " no existe."));
    }

    public Biometria obtenerPorUsuarioId(Long usuarioId) {
        Biometria biometria = biometriaRepository.findByUsuarioId(usuarioId);
        if (biometria == null) {
            throw new RuntimeException("Error: No se encontraron registros biométricos para el usuario con ID " + usuarioId);
        }
        return biometria;
    }

    public void eliminar(Long id) {
        Biometria biometria = obtenerPorId(id);
        biometriaRepository.delete(biometria);
    }
}