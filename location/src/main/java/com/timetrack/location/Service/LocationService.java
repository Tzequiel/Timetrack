package com.timetrack.location.Service;
import com.timetrack.location.Model.LocationRequest;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    
    public String validarUbicacion(LocationRequest request) {

        double diferenciaLat = Math.abs(request.getLatitudCelular() - request.getLatitudSucursal());
        double diferenciaLon = Math.abs(request.getLongitudCelular() - request.getLongitudSucursal());
        
        if (diferenciaLat < 0.005 && diferenciaLon < 0.005) {
            return "Ubicacion Aprobada: Empleado dentro de la geocerca.";
        }
        return "Ubicacion Rechazada: Empleado fuera del rango permitido.";
    }
}