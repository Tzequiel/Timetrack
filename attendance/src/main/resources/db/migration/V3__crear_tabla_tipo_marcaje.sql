
CREATE TABLE tipo_marcaje (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
descripcion VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO tipo_marcaje (id, descripcion) VALUES
(1, 'Clock-In'),
(2, 'Clock-Out'),
(3, 'Break-Start'),
(4, 'Break-End');

ALTER TABLE asistencia
ADD CONSTRAINT fk_asistencia_tipo_marcaje
FOREIGN KEY (TIPO_MARCAJE_id) REFERENCES tipo_marcaje(id);