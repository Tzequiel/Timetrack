package com.timetrack.maintenance.Repository;

import com.timetrack.maintenance.Model.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {
}
