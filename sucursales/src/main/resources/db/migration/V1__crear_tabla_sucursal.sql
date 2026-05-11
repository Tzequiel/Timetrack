CREATE TABLE SUCURSAL (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    latitud_centro DOUBLE NOT NULL,
    longitud_centro DOUBLE NOT NULL,
    radio_tolerancia_metros INT NOT NULL,
    EMPRESA_id BIGINT NOT NULL
);