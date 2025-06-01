--liquibase formatted sql

--changeset sanzhar:3
CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    createdat TIMESTAMP NOT NULL,
    userid BIGINT REFERENCES users(id)
);





