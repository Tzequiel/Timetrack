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

    // Método para guardar un nuevo marcaje
    public Asistencia registrarMarcaje(Asistencia nuevaAsistencia) {
        // Le asignamos la hora y fecha exacta del servidor en el momento que marca
        nuevaAsistencia.setFechaHoraMarcaje(LocalDateTime.now());
        
        // Guardamos en la base de datos
        return asistenciaRepository.save(nuevaAsistencia);
    }

    // Método para ver todos los marcajes (para RRHH por ejemplo)
    public List<Asistencia> obtenerTodosLosMarcajes() {
        return asistenciaRepository.findAll();
    }

    // Método para ver los marcajes de un empleado en específico
    public List<Asistencia> obtenerMarcajesPorEmpleado(Long usuarioId) {
        return asistenciaRepository.findByUsuarioId(usuarioId);
    }
}