CREATE TABLE ASISTENCIA (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fecha_hora_marcaje DATETIME NOT NULL,
    latitud_marca DOUBLE NOT NULL,
    longitud_marca DOUBLE NOT NULL,
    validacion_biometrica VARCHAR(50) NOT NULL,
    validacion_gps VARCHAR(50) NOT NULL,
    USUARIO_id BIGINT NOT NULL,
    TIPO_MARCAJE_id BIGINT NOT NULL
);