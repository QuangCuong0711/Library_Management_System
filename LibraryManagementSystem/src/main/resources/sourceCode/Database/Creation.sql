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
    description     TEXT,
    quantity        INT
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
CREATE TABLE IF NOT EXISTS ADMIN
(
    adminId        VARCHAR(10) PRIMARY KEY,
    name           VARCHAR(50),
    identityNumber VARCHAR(20),
    birth          DATE,
    gender         VARCHAR(6),
    phoneNumber    VARCHAR(20),
    email          VARCHAR(50),
    address        VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS ADMINACCOUNT
(
    adminAccountId  VARCHAR(10) PRIMARY KEY,
    nameAccount     VARCHAR(50),
    passwordAccount VARCHAR(50),
    adminId         VARCHAR(10),
    CONSTRAINT fk_admin FOREIGN KEY (adminId) REFERENCES ADMIN(adminId)
);
CREATE TABLE IF NOT EXISTS USERACCOUNT
(
    userAccountId  VARCHAR(10) PRIMARY KEY,
    nameAccount     VARCHAR(50),
    passwordAccount VARCHAR(50),
    userId         VARCHAR(10),
    CONSTRAINT fk_user FOREIGN KEY (userId) REFERENCES USER(userId)
);
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS ADMIN;
DROP TABLE IF EXISTS ADMINACCOUNT;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS USERACCOUNT;
