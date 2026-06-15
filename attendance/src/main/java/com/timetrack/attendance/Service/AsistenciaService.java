package com.timetrack.attendance.Service;

import com.timetrack.attendance.Client.LocationClient;
import com.timetrack.attendance.Client.ManagClient;
import com.timetrack.attendance.Dto.LocationValidationDto;
import com.timetrack.attendance.Dto.UsuarioDto;
import com.timetrack.attendance.Model.Asistencia;
import com.timetrack.attendance.Repository.AsistenciaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private ManagClient managClient;

    @Autowired
    private LocationClient locationClient;

    public Asistencia registrarMarcaje(Asistencia nuevaAsistencia, Long tipoMarcajeId) {

        UsuarioDto empleado;

        // 1. valida usuario en manag
        try {
            empleado = managClient.verPorId(nuevaAsistencia.getUsuarioId());
            System.out.println("Empleado encontrado: " + empleado.getNombre());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Error: El empleado con ID " + nuevaAsistencia.getUsuarioId() + " no existe.");
        }

        // 2. valida ubicacion en location
        LocationValidationDto locRequest = new LocationValidationDto();
        locRequest.setSucursalId(empleado.getSucursalId());
        locRequest.setLatitudCelular(nuevaAsistencia.getLatitudMarca());
        locRequest.setLongitudCelular(nuevaAsistencia.getLongitudMarca());

        try {
            String resultadoGps = locationClient.validarUbicacion(locRequest);
            nuevaAsistencia.setValidacionGps(resultadoGps);
        } catch (FeignException.Unauthorized | FeignException.BadRequest e) {
            String mensajeError = e.contentUTF8();
            nuevaAsistencia.setValidacionGps(mensajeError);
            throw new RuntimeException("Marcaje rechazado por GPS: " + mensajeError);
        }

        // 3. guarda marcaje
        nuevaAsistencia.setFechaHoraMarcaje(LocalDateTime.now());
        nuevaAsistencia.setTipoMarcajeId(tipoMarcajeId);

        // (La validación biométrica la dejaremos pendiente por ahora hasta integrar ese flujo)
        if (nuevaAsistencia.getValidacionBiometrica() == null) {
            nuevaAsistencia.setValidacionBiometrica("PENDIENTE");
        }

        return asistenciaRepository.save(nuevaAsistencia);
    }

    public List<Asistencia> obtenerTodosLosMarcajes() {
        return asistenciaRepository.findAll();
    }

    public List<Asistencia> obtenerMarcajesPorEmpleado(Long usuarioId) {
        return asistenciaRepository.findByUsuarioId(usuarioId);
    }

    public Asistencia obtenerPorId(Long id) {
        return asistenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El marcaje de asistencia con ID " + id + " no existe."));
    }

    public Asistencia actualizar(Long id, Asistencia detalles) {
        Asistencia asistencia = obtenerPorId(id);

        asistencia.setFechaHoraMarcaje(detalles.getFechaHoraMarcaje());
        asistencia.setLatitudMarca(detalles.getLatitudMarca());
        asistencia.setLongitudMarca(detalles.getLongitudMarca());
        asistencia.setValidacionBiometrica(detalles.getValidacionBiometrica());
        asistencia.setValidacionGps(detalles.getValidacionGps());
        asistencia.setUsuarioId(detalles.getUsuarioId());
        asistencia.setTipoMarcajeId(detalles.getTipoMarcajeId());

        return asistenciaRepository.save(asistencia);
    }

    public void eliminar(Long id) {
        Asistencia asistencia = obtenerPorId(id);
        asistenciaRepository.delete(asistencia);
    }
}