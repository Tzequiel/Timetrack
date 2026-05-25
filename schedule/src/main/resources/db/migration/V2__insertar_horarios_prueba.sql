INSERT INTO dia_semana (id, nombre) VALUES
(1, 'Lunes'),
(2, 'Martes'),
(3, 'Miércoles'),
(4, 'Jueves'),
(5, 'Viernes'),
(6, 'Sábado'),
(7, 'Domingo');

INSERT INTO horario_turno (hora_entrada, hora_salida, usuario_id, dia_semana_id) VALUES
('08:30', '18:30', 1, 1),
('08:30', '18:30', 1, 2),
('08:30', '18:30', 1, 3),
('08:30', '18:30', 1, 4),
('08:30', '18:30', 1, 5);