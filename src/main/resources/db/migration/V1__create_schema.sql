-- V1__create_schema.sql
-- Schema creation for VetCare API database tables matching JPA entities

CREATE TABLE IF NOT EXISTS tb_users (
    use_id BIGSERIAL PRIMARY KEY,
    use_name VARCHAR(255) NOT NULL,
    use_email VARCHAR(255) UNIQUE NOT NULL,
    use_password VARCHAR(255) NOT NULL,
    use_typeuser VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_animals (
    ani_id BIGSERIAL PRIMARY KEY,
    ani_name VARCHAR(255) NOT NULL,
    ani_specie VARCHAR(50) NOT NULL,
    ani_race VARCHAR(255),
    ani_age INT,
    ani_weight DOUBLE PRECISION,
    ani_gender VARCHAR(10),
    ani_owner_id BIGINT,
    CONSTRAINT fk_animal_owner FOREIGN KEY (ani_owner_id) REFERENCES tb_users(use_id)
);

CREATE TABLE IF NOT EXISTS tb_checkup_initial (
    inc_id BIGSERIAL PRIMARY KEY,
    inc_examinationdate TIMESTAMP NOT NULL,
    inc_species VARCHAR(20),
    inc_sex VARCHAR(3),
    inc_animal_id BIGINT,
    inc_vet_id BIGINT,
    inc_crt VARCHAR(50),
    inc_rr VARCHAR(50),
    inc_hr VARCHAR(50),
    inc_tpc VARCHAR(50),
    inc_hydration VARCHAR(100),
    inc_arterialpulse VARCHAR(50),
    inc_mucousmembranes VARCHAR(50),
    inc_consciousnesslevel VARCHAR(50),
    inc_nutritionalstatus VARCHAR(50),
    inc_behavior VARCHAR(50),
    inc_hasectoparasites BOOLEAN,
    inc_ectoparasitesdetails VARCHAR(255),
    inc_posturemovement VARCHAR(50),
    inc_eyes VARCHAR(50),
    inc_ears VARCHAR(50),
    inc_oralcavity VARCHAR(50),
    inc_respiratory VARCHAR(50),
    inc_circulatory VARCHAR(50),
    inc_hemolymphatic VARCHAR(50),
    inc_digestive VARCHAR(50),
    inc_genital VARCHAR(50),
    inc_urinary VARCHAR(50),
    inc_nervous VARCHAR(50),
    inc_skinandappendages VARCHAR(50),
    inc_otherstatus VARCHAR(50),
    inc_otherobservations TEXT,
    CONSTRAINT fk_checkup_animal FOREIGN KEY (inc_animal_id) REFERENCES tb_animals(ani_id),
    CONSTRAINT fk_checkup_vet FOREIGN KEY (inc_vet_id) REFERENCES tb_users(use_id)
);

CREATE TABLE IF NOT EXISTS tb_history (
    his_id BIGSERIAL PRIMARY KEY,
    his_animal_id BIGINT UNIQUE,
    his_checkupid BIGINT UNIQUE,
    CONSTRAINT fk_history_animal FOREIGN KEY (his_animal_id) REFERENCES tb_animals(ani_id),
    CONSTRAINT fk_history_checkup FOREIGN KEY (his_checkupid) REFERENCES tb_checkup_initial(inc_id)
);

CREATE TABLE IF NOT EXISTS tb_medicine (
    med_id BIGSERIAL PRIMARY KEY,
    med_name VARCHAR(255) UNIQUE NOT NULL,
    med_manufacturer VARCHAR(255),
    med_type VARCHAR(50),
    med_indicatedspecies VARCHAR(255),
    med_dosage VARCHAR(255),
    med_animal_id BIGINT,
    CONSTRAINT fk_medicine_animal FOREIGN KEY (med_animal_id) REFERENCES tb_animals(ani_id)
);

CREATE TABLE IF NOT EXISTS tb_vaccine (
    vac_id BIGSERIAL PRIMARY KEY,
    vac_vaccinename VARCHAR(70) NOT NULL,
    vac_description VARCHAR(250) NOT NULL,
    vac_numofdoses INT NOT NULL,
    vac_dayinterval INT NOT NULL
);
