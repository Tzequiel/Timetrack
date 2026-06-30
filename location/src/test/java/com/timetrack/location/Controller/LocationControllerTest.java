package com.timetrack.location.Controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.timetrack.location.Model.Geocerca;
import com.timetrack.location.Model.LocationRequest;
import com.timetrack.location.Service.LocationService;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @Test
    @DisplayName("POST /api/location/geofence -> Exito")
    public void crearOActualizarGeofence_Exito() throws Exception {
        var geocercaMock = new Geocerca();
        geocercaMock.setId(1L);
        geocercaMock.setRadioMetros(100.0);

        when(locationService.guardarOActualizarGeofence(any(Geocerca.class))).thenReturn(geocercaMock);

        mockMvc.perform(post("/api/location/geofence")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sucursalId\": 1, \"latitud\": -41.4, \"longitud\": -72.9, \"radioMetros\": 100.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/location/geofence -> Error")
    public void crearOActualizarGeofence_Error() throws Exception {
        mockMvc.perform(post("/api/location/geofence")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/location/validate -> Exito")
    public void validar_Exito() throws Exception {
        when(locationService.validarUbicacion(any(LocationRequest.class)))
                .thenReturn("Ubicacion Aprobada: Empleado dentro de la geocerca.");

        mockMvc.perform(post("/api/location/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sucursalId\": 1, \"latitudCelular\": -41.4, \"longitudCelular\": -72.9}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ubicacion Aprobada: Empleado dentro de la geocerca."));
    }

    @Test
    @DisplayName("POST /api/location/validate -> Error (Rechazada)")
    public void validar_Error() throws Exception {
        when(locationService.validarUbicacion(any(LocationRequest.class)))
                .thenReturn("Error: No existe una geocerca parametrizada");

        mockMvc.perform(post("/api/location/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sucursalId\": 99, \"latitudCelular\": -41.4, \"longitudCelular\": -72.9}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: No existe una geocerca parametrizada"));
    }

    @Test
    @DisplayName("GET /api/location/geofence -> Exito")
    public void obtenerTodasLasGeocercas_Exito() throws Exception {
        var geocercaMock = new Geocerca();
        geocercaMock.setId(1L);

        when(locationService.obtenerTodas()).thenReturn(List.of(geocercaMock));

        mockMvc.perform(get("/api/location/geofence")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/location/geofence -> Error")
    public void obtenerTodasLasGeocercas_Error() throws Exception {
        mockMvc.perform(get("/api/location/geofence")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @DisplayName("GET /api/location/geofence/{id} -> Exito")
    public void obtenerGeocercaPorId_Exito() throws Exception {
        var geocercaMock = new Geocerca();
        geocercaMock.setId(5L);

        when(locationService.obtenerPorId(5L)).thenReturn(geocercaMock);

        mockMvc.perform(get("/api/location/geofence/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @DisplayName("GET /api/location/geofence/{id} -> Error")
    public void obtenerGeocercaPorId_Error() throws Exception {
        when(locationService.obtenerPorId(99L)).thenThrow(new RuntimeException("Error: Geocerca no encontrada"));

        mockMvc.perform(get("/api/location/geofence/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Geocerca no encontrada"));
    }

    @Test
    @DisplayName("PUT /api/location/geofence/{id} -> Exito")
    public void actualizarGeocerca_Exito() throws Exception {
        var geocercaActualizada = new Geocerca();
        geocercaActualizada.setId(5L);
        geocercaActualizada.setRadioMetros(200.0);

        when(locationService.actualizarGeocerca(eq(5L), any(Geocerca.class))).thenReturn(geocercaActualizada);

        mockMvc.perform(put("/api/location/geofence/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"radioMetros\": 200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.radioMetros").value(200.0));
    }

    @Test
    @DisplayName("PUT /api/location/geofence/{id} -> Error")
    public void actualizarGeocerca_Error() throws Exception {
        when(locationService.actualizarGeocerca(eq(99L), any(Geocerca.class)))
                .thenThrow(new RuntimeException("Error: Geocerca no encontrada"));

        mockMvc.perform(put("/api/location/geofence/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"radioMetros\": 200.0}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Geocerca no encontrada"));
    }

    @Test
    @DisplayName("DELETE /api/location/geofence/{id} -> Exito")
    public void eliminarGeocerca_Exito() throws Exception {
        doNothing().when(locationService).eliminarGeocerca(5L);

        mockMvc.perform(delete("/api/location/geofence/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Geocerca con ID 5 eliminada correctamente."));
    }

    @Test
    @DisplayName("DELETE /api/location/geofence/{id} -> Error")
    public void eliminarGeocerca_Error() throws Exception {
        doThrow(new RuntimeException("Error: Geocerca no encontrada")).when(locationService).eliminarGeocerca(99L);

        mockMvc.perform(delete("/api/location/geofence/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Geocerca no encontrada"));
    }
}