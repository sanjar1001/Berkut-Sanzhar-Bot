--liquibase formatted sql

--changeset sanzhar:1
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    telegram_token varchar(100),
    chat_id varchar(100)
);

--changeset sanzhar:2
CREATE TABLE ROLES (
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(255)
)




