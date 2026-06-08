package com.timetrack.notif.Repository;

import com.timetrack.notif.Model.NotificacionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionLogRepository extends JpaRepository<NotificacionLog, Long> {
    List<NotificacionLog> findByUsuarioId(Long usuarioId);
}