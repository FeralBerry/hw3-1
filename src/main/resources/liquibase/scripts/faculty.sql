--liquibase formatted sql

--changeset FeralHorse:1

CREATE INDEX faculty_name_color_index ON faculty (name, color)