package com.timetrack.biometric.Controller;

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

import com.timetrack.biometric.Model.Biometria;
import com.timetrack.biometric.Service.BiometriaService;

@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(BiometriaController.class)public class BiometriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BiometriaService biometriaService;

    @Test
    @DisplayName("POST /api/biometrics/register-face -> Exito")
    public void registrarRostro_Exito() throws Exception {
        var biometriaMock = new Biometria();
        biometriaMock.setId(1L);
        biometriaMock.setUsuarioId(10L);
        biometriaMock.setVectorFacial("vector-rostro-abc");

        when(biometriaService.registrarRostro(eq(10L), anyString())).thenReturn(biometriaMock);

        mockMvc.perform(post("/api/biometrics/register-face")
                .param("usuarioId", "10")
                .param("vectorFacial", "vector-rostro-abc"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.vectorFacial").value("vector-rostro-abc"));
    }

    @Test
    @DisplayName("POST /api/biometrics/register-face -> Error (Falta parametro)")
    public void registrarRostro_Error() throws Exception {
        mockMvc.perform(post("/api/biometrics/register-face")
                .param("usuarioId", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/biometrics/register-fingerprint -> Exito")
    public void registrarHuella_Exito() throws Exception {
        var biometriaMock = new Biometria();
        biometriaMock.setId(2L);
        biometriaMock.setUsuarioId(11L);
        biometriaMock.setHuellaDactilar("huella-xyz");

        when(biometriaService.registrarHuella(eq(11L), anyString())).thenReturn(biometriaMock);

        mockMvc.perform(post("/api/biometrics/register-fingerprint")
                .param("usuarioId", "11")
                .param("huellaDactilar", "huella-xyz"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.huellaDactilar").value("huella-xyz"));
    }

    @Test
    @DisplayName("POST /api/biometrics/register-fingerprint -> Error (Falta parametro)")
    public void registrarHuella_Error() throws Exception {
        mockMvc.perform(post("/api/biometrics/register-fingerprint")
                .param("huellaDactilar", "huella-xyz"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/biometrics/verify-face -> Exito")
    public void verificarRostro_Exito() throws Exception {
        when(biometriaService.verificarRostro(eq(10L), anyString())).thenReturn(true);

        mockMvc.perform(post("/api/biometrics/verify-face")
                .param("usuarioId", "10")
                .param("vectorFacial", "vector-rostro-abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("Verificación Facial Exitosa"));
    }

    @Test
    @DisplayName("POST /api/biometrics/verify-face -> Error (No coincide)")
    public void verificarRostro_Error() throws Exception {
        when(biometriaService.verificarRostro(eq(10L), anyString())).thenReturn(false);

        mockMvc.perform(post("/api/biometrics/verify-face")
                .param("usuarioId", "10")
                .param("vectorFacial", "vector-incorrecto"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Fallo en Verificación Facial"));
    }

    @Test
    @DisplayName("POST /api/biometrics/verify-fingerprint -> Exito")
    public void verificarHuella_Exito() throws Exception {
        when(biometriaService.verificarHuella(eq(11L), anyString())).thenReturn(true);

        mockMvc.perform(post("/api/biometrics/verify-fingerprint")
                .param("usuarioId", "11")
                .param("huellaDactilar", "huella-xyz"))
                .andExpect(status().isOk())
                .andExpect(content().string("Verificación de Huella Dactilar Exitosa"));
    }

    @Test
    @DisplayName("POST /api/biometrics/verify-fingerprint -> Error (No coincide)")
    public void verificarHuella_Error() throws Exception {
        when(biometriaService.verificarHuella(eq(11L), anyString())).thenReturn(false);

        mockMvc.perform(post("/api/biometrics/verify-fingerprint")
                .param("usuarioId", "11")
                .param("huellaDactilar", "huella-incorrecta"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Fallo en Verificación de Huella Dactilar"));
    }

    @Test
    @DisplayName("GET /api/biometrics -> Exito")
    public void verTodas_Exito() throws Exception {
        var biometriaMock = new Biometria();
        biometriaMock.setId(1L);

        when(biometriaService.obtenerTodas()).thenReturn(List.of(biometriaMock));

        mockMvc.perform(get("/api/biometrics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/biometrics -> Error")
    public void verTodas_Error() throws Exception {
        mockMvc.perform(get("/api/biometrics")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @DisplayName("GET /api/biometrics/{id} -> Exito")
    public void verPorId_Exito() throws Exception {
        var biometriaMock = new Biometria();
        biometriaMock.setId(5L);

        when(biometriaService.obtenerPorId(5L)).thenReturn(biometriaMock);

        mockMvc.perform(get("/api/biometrics/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @DisplayName("GET /api/biometrics/{id} -> Error")
    public void verPorId_Error() throws Exception {
        mockMvc.perform(get("/api/biometrics/texto_invalido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/biometrics/user/{usuarioId} -> Exito")
    public void verPorUsuarioId_Exito() throws Exception {
        var biometriaMock = new Biometria();
        biometriaMock.setUsuarioId(10L);

        when(biometriaService.obtenerPorUsuarioId(10L)).thenReturn(biometriaMock);

        mockMvc.perform(get("/api/biometrics/user/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(10));
    }

    @Test
    @DisplayName("GET /api/biometrics/user/{usuarioId} -> Error")
    public void verPorUsuarioId_Error() throws Exception {
        mockMvc.perform(get("/api/biometrics/user/texto_invalido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/biometrics/{id} -> Exito")
    public void eliminar_Exito() throws Exception {
        doNothing().when(biometriaService).eliminar(5L);

        mockMvc.perform(delete("/api/biometrics/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Registro biométrico eliminado correctamente"));
    }

    @Test
    @DisplayName("DELETE /api/biometrics/{id} -> Error")
    public void eliminar_Error() throws Exception {
        mockMvc.perform(delete("/api/biometrics/texto_invalido"))
                .andExpect(status().isBadRequest());
    }
}