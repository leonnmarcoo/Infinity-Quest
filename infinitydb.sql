CREATE DATABASE IF NOT EXISTS infinitydb;
USE infinitydb;

-- Admin Table
CREATE TABLE Admin (
	adminID INT AUTO_INCREMENT PRIMARY KEY,
	adminName VARCHAR(30) NOT NULL,
	adminPassword VARCHAR(30) NOT NULL
);

INSERT INTO Admin (adminName, adminPassword)
VALUES ('username', 'password');

-- User Table
CREATE TABLE User (
	userID INT AUTO_INCREMENT PRIMARY KEY,
	userName VARCHAR(30) UNIQUE,
	userPassword VARCHAR(15),
	userEmail VARCHAR(25),
	userBio VARCHAR(60),
	userProfile VARCHAR(1000)
);

INSERT INTO User (userName, userPassword, userEmail, userBio)
VALUES ('mikey', '2023', 'johnmikael@gmail.com', 'First One!');

-- Director Table
CREATE TABLE Director (
    directorID INT AUTO_INCREMENT PRIMARY KEY,
    directorName VARCHAR(50) UNIQUE
);

-- Actor Table
CREATE TABLE Actor (
	actorID INT AUTO_INCREMENT PRIMARY KEY,
    actorName VARCHAR(50) UNIQUE
);

-- Role Table
CREATE TABLE Role (
	roleID INT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(50) UNIQUE
);

-- Content Table
CREATE TABLE Content (
    contentID INT AUTO_INCREMENT PRIMARY KEY,
    contentTitle VARCHAR(250),
    contentPoster VARCHAR(1000),
    contentRuntime VARCHAR(30),
    contentReleaseDate VARCHAR(15),
    contentSynopsis VARCHAR(2000),
    contentSeason INT,
    contentEpisode INT,
    contentAgeRating VARCHAR(10),
    directorID INT,
    contentTrailer VARCHAR(1000),
    contentChronologicalOrder INT,
    contentPhase INT,
    FOREIGN KEY (directorID) REFERENCES Director(directorID)
);

-- Cast Table
CREATE TABLE Cast (
    castID INT AUTO_INCREMENT PRIMARY KEY,
    actorID INT NOT NULL,
    roleID INT NOT NULL,
    contentID INT NOT NULL,
    FOREIGN KEY (actorID) REFERENCES Actor(actorID) ON DELETE CASCADE,
    FOREIGN KEY (roleID) REFERENCES Role(roleID) ON DELETE CASCADE,
    FOREIGN KEY (contentID) REFERENCES Content(contentID) ON DELETE CASCADE,
    CONSTRAINT unique_actor_role_content UNIQUE (actorID, roleID, contentID)
);

-- Watchlist Table
CREATE TABLE Watchlist (
    watchlistID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT,
    contentID INT,
    watchlistDateTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE,
    FOREIGN KEY (contentID) REFERENCES Content(contentID)
);

-- Watched Table
CREATE TABLE Watched (
    watchedID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT,
    contentID INT,
    watchedDateTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE,
    FOREIGN KEY (contentID) REFERENCES Content(contentID)
);

-- Rating Table
CREATE TABLE Rating (
    ratingID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT,
    contentID INT,
    star INT,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE,
    FOREIGN KEY (contentID) REFERENCES Content(contentID),
    CONSTRAINT starRating CHECK (star >= 1 AND star <= 5)
);

-- Review Table
CREATE TABLE Review (
    reviewID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT,
    contentID INT,
    reviewText TEXT,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE,
    FOREIGN KEY (contentID) REFERENCES Content(contentID)
);

-- Like Table
CREATE TABLE userLike (
    likeID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT,
    contentID INT,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE,
    FOREIGN KEY (contentID) REFERENCES Content(contentID)
);

-- Dislike Table
CREATE TABLE userDislike (
    dislikeID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT,
    contentID INT,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE,
    FOREIGN KEY (contentID) REFERENCES Content(contentID)
);
