DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR (50) UNIQUE NOT NULL,
    password VARCHAR (64) NOT NULL,
    verified INT NOT NULL default 0,
    version INT NOT NULL,
    created_on TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);
