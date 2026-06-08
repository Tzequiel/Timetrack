package com.timetrack.metrics.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPORTE_EXPORTADO")
public class ReporteExportado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_reporte")
    private String tipoReporte; // PDF, EXCEL, CSV

    private String periodo; // Ejemplo: "Mayo 2026"

    @Column(name = "fecha_exportacion")
    private LocalDateTime fechaExportacion;

    private String estado;

    // Constructores, Getters y Setters
    public ReporteExportado() {}

    public ReporteExportado(String tipoReporte, String periodo, LocalDateTime fechaExportacion, String estado) {
        this.tipoReporte = tipoReporte;
        this.periodo = periodo;
        this.fechaExportacion = fechaExportacion;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoReporte() { return tipoReporte; }
    public void setTipoReporte(String tipoReporte) { this.tipoReporte = tipoReporte; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public LocalDateTime getFechaExportacion() { return fechaExportacion; }
    public void setFechaExportacion(LocalDateTime fechaExportacion) { this.fechaExportacion = fechaExportacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}