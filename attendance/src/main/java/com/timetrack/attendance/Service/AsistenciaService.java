package com.timetrack.attendance.Service;

import com.timetrack.attendance.Model.Asistencia;
import com.timetrack.attendance.Repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public Asistencia registrarMarcaje(Asistencia nuevaAsistencia) {
        nuevaAsistencia.setFechaHoraMarcaje(LocalDateTime.now());
        
        return asistenciaRepository.save(nuevaAsistencia);
    }

    public List<Asistencia> obtenerTodosLosMarcajes() {
        return asistenciaRepository.findAll();
    }

    public List<Asistencia> obtenerMarcajesPorEmpleado(Long usuarioId) {
        return asistenciaRepository.findByUsuarioId(usuarioId);
    }
}