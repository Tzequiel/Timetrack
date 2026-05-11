package com.timetrack.attendance.Repository;

import com.timetrack.attendance.Model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    
    // Spring Boot es tan inteligente que si le pones este nombre al método,
    // él solo crea la consulta: SELECT * FROM ASISTENCIA WHERE USUARIO_id = ?
    List<Asistencia> findByUsuarioId(Long usuarioId);
}