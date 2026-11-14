-- V6__create_table_images.sql

CREATE TABLE tb_image (
    ima_id SERIAL PRIMARY KEY,
    ima_nome VARCHAR(255) NOT NULL,
    ima_tipo_conteudo VARCHAR(100) NOT NULL,
    ima_dados BYTEA NOT NULL
);