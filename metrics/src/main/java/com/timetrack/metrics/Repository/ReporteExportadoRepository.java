package com.timetrack.metrics.Repository;

import com.timetrack.metrics.Model.ReporteExportado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteExportadoRepository extends JpaRepository<ReporteExportado, Long> {
}