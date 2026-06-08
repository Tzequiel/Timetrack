package com.timetrack.schedule.Repository;

import com.timetrack.schedule.Model.HorarioTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HorarioTurnoRepository extends JpaRepository<HorarioTurno, Long> {
    List<HorarioTurno> findByUsuarioId(Long usuarioId);
}