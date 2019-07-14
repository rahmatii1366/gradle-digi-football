CREATE DATABASE digi_football_db;

DROP TABLE IF EXISTS users;
CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    email VARCHAR (50) UNIQUE NOT NULL,
    password VARCHAR (64) NOT NULL,
    verified INTEGER NOT NULL default 0,
    created_on TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);

select * from users;