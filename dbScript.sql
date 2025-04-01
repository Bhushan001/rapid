show databases;

use mydb;
show tables;

create database mydb;
drop database mydb;

DROP TABLE roles;
DROP TABLE user_roles;

CREATE TABLE roles (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS mydb.user_roles (
    user_id BINARY(16) REFERENCES mydb.users(id),
    role_id BINARY(16) REFERENCES mydb.roles(id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO mydb.Clients (client_id, client_name, client_description) VALUES
(UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174456'), 'RAPID', 'The main client');

INSERT INTO mydb.roles (id, name) VALUES
(UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174000'), 'USER'),
(UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174001'), 'MANAGER'),
(UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174002'), 'ADMIN'),
(UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174003'), 'SUPER_ADMIN');



select * from clients;
select * from roles;
select * from user_roles;
select * from users;
select * from workspaces;
select * from projects;
select * from pages;
select * from requestschemas;
select * from s1schemas;
select * from mapping;


SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE clients;
TRUNCATE TABLE users;
TRUNCATE TABLE user_roles;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE Clients (
    clientId CHAR(36) PRIMARY KEY,
    clientName VARCHAR(255) NOT NULL,
    clientDescription TEXT
);
