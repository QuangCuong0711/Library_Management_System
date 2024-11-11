CREATE DATABASE IF NOT EXISTS Library;
USE Library;
CREATE TABLE IF NOT EXISTS Book (
    ISBN VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    publisher VARCHAR(255),
    publicationDate DATE,
    language VARCHAR(50),
    pageNumber INT,
    imageUrl VARCHAR(255),
    description TEXT
);
CREATE TABLE IF NOT EXISTS User (
    name VARCHAR(255),
    id VARCHAR(50) PRIMARY KEY,
    birthDate DATE,
    phoneNumber VARCHAR(20),
    indentityCard VARCHAR(50),
    address VARCHAR(255),
    gender VARCHAR(10)
);