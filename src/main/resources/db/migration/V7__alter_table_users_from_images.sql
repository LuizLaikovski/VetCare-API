-- V7__add_image_to_users.sql

ALTER TABLE tb_users
    ADD COLUMN use_image_id BIGINT;

ALTER TABLE tb_users
    ADD CONSTRAINT fk_user_image
        FOREIGN KEY (use_image_id)
            REFERENCES tb_image(ima_id);