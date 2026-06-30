package com.timetrack.sucursales.Controller;

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

import com.timetrack.sucursales.Model.Sucursal;
import com.timetrack.sucursales.Service.SucursalService;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(SucursalController.class)
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SucursalService sucursalService;

    @Test
    @DisplayName("POST /api/branches -> Exito")
    public void registrarSucursal_Exito() throws Exception {
        var sucursalMock = new Sucursal();
        sucursalMock.setId(1L);
        sucursalMock.setNombre("Sucursal Centro");

        when(sucursalService.crear(any(Sucursal.class))).thenReturn(sucursalMock);

        mockMvc.perform(post("/api/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Sucursal Centro\", \"direccion\": \"Avenida Principal 123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Sucursal Centro"));
    }

    @Test
    @DisplayName("POST /api/branches -> Error")
    public void registrarSucursal_Error() throws Exception {
        mockMvc.perform(post("/api/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/branches -> Exito")
    public void verTodas_Exito() throws Exception {
        var sucursalMock = new Sucursal();
        sucursalMock.setId(1L);

        when(sucursalService.listarTodas()).thenReturn(List.of(sucursalMock));

        mockMvc.perform(get("/api/branches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/branches -> Error")
    public void verTodas_Error() throws Exception {
        mockMvc.perform(get("/api/branches")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @DisplayName("PUT /api/branches/{branchId} -> Exito")
    public void actualizarSucursal_Exito() throws Exception {
        var sucursalActualizada = new Sucursal();
        sucursalActualizada.setId(5L);
        sucursalActualizada.setNombre("Sucursal Norte");

        when(sucursalService.actualizar(eq(5L), any(Sucursal.class))).thenReturn(sucursalActualizada);

        mockMvc.perform(put("/api/branches/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Sucursal Norte\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sucursal Norte"));
    }

    @Test
    @DisplayName("PUT /api/branches/{branchId} -> Error")
    public void actualizarSucursal_Error() throws Exception {
        mockMvc.perform(put("/api/branches/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("JSON_INVALIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/branches/{branchId} -> Exito")
    public void obtenerPorId_Exito() throws Exception {
        var sucursalMock = new Sucursal();
        sucursalMock.setId(5L);

        when(sucursalService.obtenerPorId(5L)).thenReturn(sucursalMock);

        mockMvc.perform(get("/api/branches/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @DisplayName("GET /api/branches/{branchId} -> Error")
    public void obtenerPorId_Error() throws Exception {
        mockMvc.perform(get("/api/branches/texto_invalido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/branches/{branchId} -> Exito")
    public void eliminarSucursal_Exito() throws Exception {
        doNothing().when(sucursalService).eliminar(5L);

        mockMvc.perform(delete("/api/branches/5"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/branches/{branchId} -> Error")
    public void eliminarSucursal_Error() throws Exception {
        mockMvc.perform(delete("/api/branches/texto_invalido"))
                .andExpect(status().isBadRequest());
    }
}