package com.timetrack.attendance.logger;

import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

private static final Logger log =
        LoggerFactory.getLogger(MiClase.class);
log.info("Usuario creado correctamente");
log.warn("Fallo de validación en tiempo de persistencia");
log.error("Error interno del servidor: {}", ex.getMessage());