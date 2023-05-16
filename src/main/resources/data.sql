CREATE DATABASE travel-db;

CREATE TABLE card
(
    id  INT PRIMARY KEY NOT NULL,
    title VARCHAR(100),
    description VARCHAR(100),
    image VARCHAR(255)
)