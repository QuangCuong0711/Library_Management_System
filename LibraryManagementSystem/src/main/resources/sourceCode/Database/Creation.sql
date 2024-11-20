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
    userId          VARCHAR(10) PRIMARY KEY,
    name            VARCHAR(50),
    identityNumber  VARCHAR(20),
    birth           DATE,
    gender          VARCHAR(6),
    phoneNumber     VARCHAR(20),
    email           VARCHAR(50),
    address         VARCHAR(255),
    password VARCHAR(50)
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
    address        VARCHAR(255),
    password       VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS TICKET
(
    ticketId   INT AUTO_INCREMENT PRIMARY KEY,
    userId     VARCHAR(10),
    bookId     VARCHAR(20),
    borrowDate DATE,
    returnDate DATE,
    status     VARCHAR(20),
    FOREIGN KEY (userId) REFERENCES USER (userId),
    FOREIGN KEY (bookId) REFERENCES BOOK (ISBN)
);
CREATE TABLE IF NOT EXISTS FEEDBACK
(
    feedbackId INT AUTO_INCREMENT PRIMARY KEY,
    userId     VARCHAR(10),
    bookId     VARCHAR(20),
    comment    TEXT,
    rating     INT,
    date       DATE,
    FOREIGN KEY (userId) REFERENCES USER (userId),
    FOREIGN KEY (bookId) REFERENCES BOOK (ISBN)
);
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS ADMIN;
DROP TABLE IF EXISTS TICKET;
DROP TABLE IF EXISTS FEEDBACK;
DROP DATABASE IF EXISTS LIBRARY;
