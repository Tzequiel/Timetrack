-- ==============================================================================
-- 1. TABLAS DE CATÁLOGO (Las que no dependen de nadie)
-- ==============================================================================

CREATE TABLE ESTADO_SUSCRIPCION (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL
);

CREATE TABLE DIA_SEMANA (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_dia_semana VARCHAR(20) NOT NULL
);

CREATE TABLE TIPO_MARCAJE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_tipo_marcaje VARCHAR(50) NOT NULL
);

-- ==============================================================================
-- 2. TABLAS PRINCIPALES (Con Multi-Tenant)
-- ==============================================================================

CREATE TABLE EMPRESA (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rut_empresa VARCHAR(20) UNIQUE NOT NULL,
    razon_social VARCHAR(100) NOT NULL,
    logo_url VARCHAR(255),
    color_primario VARCHAR(7),
    ESTADO_SUSCRIPCION_id INT NOT NULL,
    FOREIGN KEY (ESTADO_SUSCRIPCION_id) REFERENCES ESTADO_SUSCRIPCION(id)
);

CREATE TABLE SUCURSAL (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    latitud_centro DECIMAL(10,8),
    longitud_centro DECIMAL(11,8),
    radio_tolerancia_metros INT,
    EMPRESA_id INT NOT NULL,
    FOREIGN KEY (EMPRESA_id) REFERENCES EMPRESA(id) ON DELETE CASCADE
);

CREATE TABLE ROL (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    EMPRESA_id INT NOT NULL,
    FOREIGN KEY (EMPRESA_id) REFERENCES EMPRESA(id) ON DELETE CASCADE
);

-- ==============================================================================
-- 3. TABLA USUARIO
-- ==============================================================================

CREATE TABLE USUARIO (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    EMPRESA_id INT NOT NULL,
    ROL_id INT NOT NULL,
    SUCURSAL_id INT,
    FOREIGN KEY (EMPRESA_id) REFERENCES EMPRESA(id) ON DELETE CASCADE,
    FOREIGN KEY (ROL_id) REFERENCES ROL(id),
    FOREIGN KEY (SUCURSAL_id) REFERENCES SUCURSAL(id)
);

-- ==============================================================================
-- 4. TABLAS TRANSACCIONALES Y DE NEGOCIO
-- ==============================================================================

CREATE TABLE BIOMETRIA (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vector_facial LONGTEXT, -- LONGTEXT en MySQL es ideal para guardar el CLOB/JSON del rostro
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    USUARIO_id INT NOT NULL UNIQUE,
    FOREIGN KEY (USUARIO_id) REFERENCES USUARIO(id) ON DELETE CASCADE
);

CREATE TABLE HORARIO_TURNO (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hora_entrada TIME NOT NULL, -- Cambiado de TIMESTAMP a TIME para guardar solo la hora (ej: 08:00:00)
    hora_salida TIME NOT NULL,
    USUARIO_id INT NOT NULL,
    DIA_SEMANA_id INT NOT NULL,
    FOREIGN KEY (USUARIO_id) REFERENCES USUARIO(id) ON DELETE CASCADE,
    FOREIGN KEY (DIA_SEMANA_id) REFERENCES DIA_SEMANA(id)
);

CREATE TABLE ASISTENCIA (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora_marcaje TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    latitud_marca DECIMAL(10,8),
    longitud_marca DECIMAL(11,8),
    validacion_biometrica CHAR(1) DEFAULT '0', -- 1 o 0 (True/False)
    validacion_gps CHAR(1) DEFAULT '0', -- 1 o 0 (True/False)
    USUARIO_id INT NOT NULL,
    TIPO_MARCAJE_id INT NOT NULL,
    FOREIGN KEY (USUARIO_id) REFERENCES USUARIO(id) ON DELETE CASCADE,
    FOREIGN KEY (TIPO_MARCAJE_id) REFERENCES TIPO_MARCAJE(id)
);

-- ==============================================================================
-- 5. INSERCIÓN DE CATÁLOGOS BASE (Para que el Docker levante listo para usar)
-- ==============================================================================

-- Poblar Estados de Suscripción
INSERT INTO ESTADO_SUSCRIPCION (nombre_estado) VALUES ('ACTIVO'), ('SUSPENDIDO'), ('PRUEBA_GRATIS');

-- Poblar Días de la Semana
INSERT INTO DIA_SEMANA (nombre_dia_semana) VALUES ('LUNES'), ('MARTES'), ('MIÉRCOLES'), ('JUEVES'), ('VIERNES'), ('SÁBADO'), ('DOMINGO');

-- Poblar Tipos de Marcaje
INSERT INTO TIPO_MARCAJE (nombre_tipo_marcaje) VALUES ('ENTRADA'), ('SALIDA'), ('INICIO_COLACION'), ('FIN_COLACION');