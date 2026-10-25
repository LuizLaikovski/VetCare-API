-- V5__populate_vaccination_record.sql
-- Population script seeding sample data for tb_vaccination_record

INSERT INTO tb_vaccination_record (var_aniid, var_vacid, var_medid, var_application_date, var_num_of_doses, var_observation, var_application_status) VALUES
(1, 51, 1, '2025-01-10 10:30:00', 1, 'Primeira dose da vacina antirrábica aplicada sem intercorrências.', 'APPLIED'),
(2, 52, 2, '2025-01-12 14:00:00', 1, 'Vacina polivalente V8 aplicada. Animal calmo.', 'APPLIED'),
(3, 53, 21, '2025-02-01 09:15:00', 2, 'Reforço da vacina V10.', 'APPLIED'),
(4, 54, 22, '2025-02-05 11:00:00', 1, 'Vacina contra Rinotraqueíte felina.', 'APPLIED'),
(5, 55, 23, '2025-02-10 15:45:00', 1, 'Vacina antirrábica aplicada.', 'APPLIED'),
(6, 56, 24, '2025-02-15 08:30:00', 1, 'Vacina Quádrupla Felina.', 'APPLIED'),
(7, 57, 25, '2025-03-01 10:00:00', 1, 'Vacina contra Giárdia.', 'APPLIED'),
(8, 58, 1, '2025-03-03 16:20:00', 1, 'Vacina antirrábica.', 'APPLIED'),
(9, 59, 2, '2025-03-05 11:10:00', 2, 'Segunda dose polivalente V10.', 'APPLIED'),
(10, 60, 21, '2025-03-10 14:30:00', 1, 'Vacina contra Tosse dos Canis (Bronchiguard).', 'APPLIED'),
(11, 61, 22, '2025-03-12 09:50:00', 1, 'Vacinação preventiva.', 'APPLIED'),
(12, 62, 23, '2025-03-15 13:00:00', 1, 'Vacina aviária aplicada.', 'APPLIED'),
(13, 63, 24, '2025-03-20 15:00:00', 1, 'Vacina felina V3.', 'APPLIED'),
(14, 64, 25, '2025-03-22 10:15:00', 1, 'Antirrábica anual.', 'APPLIED'),
(15, 65, 1, '2025-03-25 11:45:00', 1, 'Vacina V10 aplicada.', 'APPLIED'),
(16, 66, 2, '2025-04-02 09:30:00', 1, 'Vacina antirrábica felina.', 'APPLIED'),
(17, 67, 21, '2025-04-05 14:00:00', 1, 'Vacina contra Giárdia.', 'APPLIED'),
(18, 68, 22, '2025-04-08 16:30:00', 1, 'Vacina polivalente.', 'APPLIED'),
(19, 51, 23, '2026-01-10 10:30:00', 2, 'Reforço anual da vacina antirrábica.', 'PENDING'),
(20, 52, 24, '2026-01-12 14:00:00', 2, 'Reforço anual da polivalente V8.', 'PENDING'),
(21, 53, 25, '2026-02-01 09:15:00', 3, 'Terceira dose pendente.', 'LATE'),
(22, 54, 1, '2026-02-05 11:00:00', 2, 'Reforço felino atrasado.', 'LATE');
