package com.timetrack.schedule.Service;
import com.timetrack.schedule.Model.HorarioTurno;
import com.timetrack.schedule.Repository.HorarioTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HorarioTurnoService {
    @Autowired
    private HorarioTurnoRepository horarioRepository;

    public HorarioTurno crear(HorarioTurno horario) {
        return horarioRepository.save(horario);
    }

    public List<HorarioTurno> buscarPorUsuario(Long usuarioId) {
        return horarioRepository.findByUsuarioId(usuarioId);
    }
}