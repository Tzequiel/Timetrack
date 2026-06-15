package com.timetrack.location.Service;

import com.timetrack.location.Model.Geocerca;
import com.timetrack.location.Model.LocationRequest;
import com.timetrack.location.Repository.GeocercaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private GeocercaRepository geocercaRepository;

    public Geocerca guardarOActualizarGeofence(Geocerca nuevaGeocerca) {
        Geocerca existente = geocercaRepository.findBySucursalId(nuevaGeocerca.getSucursalId());
        if (existente != null) {
            existente.setLatitud(nuevaGeocerca.getLatitud());
            existente.setLongitud(nuevaGeocerca.getLongitud());
            existente.setRadioMetros(nuevaGeocerca.getRadioMetros());
            return geocercaRepository.save(existente);
        }
        return geocercaRepository.save(nuevaGeocerca);
    }

    // Lógica para el endpoint /validate
    public String validarUbicacion(LocationRequest request) {
        Geocerca geocerca = geocercaRepository.findBySucursalId(request.getSucursalId());

        if (geocerca == null) {
            return "Error: No existe una geocerca parametrizada para la sucursal " + request.getSucursalId();
        }

        // Cálculo matemático real de distancia (Fórmula de Haversine)
        double distanciaMetros = calcularDistanciaHaversine(
                request.getLatitudCelular(), request.getLongitudCelular(),
                geocerca.getLatitud(), geocerca.getLongitud()
        );

        if (distanciaMetros <= geocerca.getRadioMetros()) {
            return "Ubicacion Aprobada: Empleado dentro de la geocerca. Distancia: " + Math.round(distanciaMetros) + " metros.";
        }
        return "Ubicacion Rechazada: Empleado fuera del rango permitido. Distancia: " + Math.round(distanciaMetros) + " metros (Máximo permitido: " + geocerca.getRadioMetros() + "m).";
    }

    // Algoritmo auxiliar Haversine
    private double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // Radio de la Tierra en metros
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public List<Geocerca> obtenerTodas() {
        return geocercaRepository.findAll();
    }

    public Geocerca obtenerPorId(Long id) {
        return geocercaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Geocerca no encontrada con ID: " + id));
    }

    public Geocerca actualizarGeocerca(Long id, Geocerca detalles) {
        Geocerca existente = obtenerPorId(id);

        existente.setLatitud(detalles.getLatitud());
        existente.setLongitud(detalles.getLongitud());
        existente.setRadioMetros(detalles.getRadioMetros());

        // Se asume que Geocerca cuenta con el setter estándar para la sucursal
        if (detalles.getSucursalId() != null) {
            existente.setSucursalId(detalles.getSucursalId());
        }

        return geocercaRepository.save(existente);
    }

    public void eliminarGeocerca(Long id) {
        Geocerca existente = obtenerPorId(id);
        geocercaRepository.delete(existente);
    }
}