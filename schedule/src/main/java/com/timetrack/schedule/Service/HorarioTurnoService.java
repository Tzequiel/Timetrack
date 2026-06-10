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

    public HorarioTurno actualizar(Long id, HorarioTurno horarioDetalles) {
        HorarioTurno horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con el ID: " + id));

        horario.setHoraEntrada(horarioDetalles.getHoraEntrada());
        horario.setHoraSalida(horarioDetalles.getHoraSalida());
        horario.setUsuarioId(horarioDetalles.getUsuarioId());
        horario.setDiaSemanaId(horarioDetalles.getDiaSemanaId());

        return horarioRepository.save(horario);
    }
    public List<HorarioTurno> obtenerTodos() {
        return horarioRepository.findAll();
    }

    public HorarioTurno obtenerPorId(Long id) {
        return horarioRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        if (!horarioRepository.existsById(id)) {
            throw new RuntimeException("Horario no encontrado con el ID: " + id);
        }
        horarioRepository.deleteById(id);
    }
}