package com.timetrack.metrics.Model;

import jakarta.validation.constraints.NotBlank;

public class ExportRequestDTO {

    @NotBlank(message = "El tipo de reporte (PDF, EXCEL, CSV) es obligatorio")
    private String tipoReporte;

    @NotBlank(message = "El período o rango de fechas es obligatorio")
    private String periodo;

    // Getters y Setters
    public String getTipoReporte() { return tipoReporte; }
    public void setTipoReporte(String tipoReporte) { this.tipoReporte = tipoReporte; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
}