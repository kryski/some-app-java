CREATE SCHEMA IF NOT EXISTS someapp;

CREATE TABLE IF NOT EXISTS someapp.users(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    CONSTRAINT pk_users_id PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS someapp.articles(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    author_id BIGINT UNSIGNED NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    creation_time DATETIME NOT NULL,
    modification_time DATETIME,
    CONSTRAINT pk_articles_id PRIMARY KEY(id),
    CONSTRAINT fk_articles_users FOREIGN KEY(author_id) REFERENCES someapp.users(id)
);