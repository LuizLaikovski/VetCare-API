-- V4__create_vaccination_record.sql
-- Create tb_vaccination_record table and foreign key constraints

CREATE TABLE IF NOT EXISTS tb_vaccination_record (
    var_id BIGSERIAL PRIMARY KEY,
    var_aniid BIGINT NOT NULL,
    var_vacid BIGINT NOT NULL,
    var_medid BIGINT NOT NULL,
    var_application_date TIMESTAMP NOT NULL,
    var_num_of_doses INT NOT NULL,
    var_observation TEXT NOT NULL,
    var_application_status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_vaccination_animal FOREIGN KEY (var_aniid) REFERENCES tb_animals(ani_id),
    CONSTRAINT fk_vaccination_vaccine FOREIGN KEY (var_vacid) REFERENCES tb_vaccine(vac_id),
    CONSTRAINT fk_vaccination_medic FOREIGN KEY (var_medid) REFERENCES tb_users(use_id)
);
