package com.timetrack.location.Controller;
import com.timetrack.location.Model.LocationRequest;
import com.timetrack.location.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping("/validate")
    public String validar(@RequestBody LocationRequest request) {
        return locationService.validarUbicacion(request);
    }
}