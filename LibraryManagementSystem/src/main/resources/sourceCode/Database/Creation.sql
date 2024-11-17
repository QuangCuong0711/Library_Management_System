CREATE DATABASE IF NOT EXISTS LIBRARY;
USE LIBRARY;
CREATE TABLE IF NOT EXISTS BOOK
(
    ISBN            VARCHAR(20) PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    author          VARCHAR(255) NOT NULL,
    genre           VARCHAR(100),
    publisher       VARCHAR(255),
    publicationDate VARCHAR(20),
    language        VARCHAR(50),
    pageNumber      INT,
    imageUrl        VARCHAR(255),
    description     TEXT
);
CREATE TABLE IF NOT EXISTS USER
(
    userId         VARCHAR(10) PRIMARY KEY,
    name           VARCHAR(50),
    identityNumber VARCHAR(20),
    birth          DATE,
    gender         VARCHAR(6),
    phoneNumber    VARCHAR(20),
    email          VARCHAR(50),
    address        VARCHAR(255)
);
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS USER;