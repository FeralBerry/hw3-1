--liquibase formatted sql

--changeset FeralHorse:1

CREATE INDEX students_name_index ON students (name);
