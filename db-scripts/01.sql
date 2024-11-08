CREATE DATABASE task_manager;
USE task_manager;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    usertype ENUM('USER', 'ADMIN') NOT NULL
);