package com.timetrack.manag.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class logger {

    private static final Logger log = LoggerFactory.getLogger(logger.class);

    public void probarMisLogs() {

        log.info("Usuario creado correctamente");

        log.warn("Fallo de validación en tiempo de persistencia");

        try {
            int calculoMalo = 10 / 0;
        } catch (Exception ex) {

            log.error("Error interno del servidor: {}", ex.getMessage());
        }
    }
}
