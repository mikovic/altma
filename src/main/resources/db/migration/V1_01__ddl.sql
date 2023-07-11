-- *for Windows
-- \! chcp 1251

-- *for Linux
-- sudo -i -u postgres
-- psql


-- \db - список табличных пространств
-- \dn - список схем
-- \dt - список таблиц
-- ALTER USER postgres PASSWORD 'admin'; - сбросить пароль

-- DROP SCHEMA IF EXISTS readerblog CASCADE;
-- CREATE SCHEMA readerblog;
--
-- SET search_path TO readerblog;




DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);


DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    provider_id VARCHAR(50),
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    nick_name  VARCHAR(50) UNIQUE,
    password   VARCHAR(80),
    email      VARCHAR(50) UNIQUE,
    image_url  TEXT,
    email_verified BOOLEAN
);
create TABLE сonfirmation_tokens (
    id         BIGSERIAL PRIMARY KEY,
    user_id  BIGSERIAL NOT NULL,
    сonfirmation_token VARCHAR(255),
    created_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON delete NO ACTION ON update NO ACTION
 );

create TABLE users_roles (
     user_id  BIGSERIAL NOT NULL,
     role_id   SERIAL NOT NULL,
     PRIMARY KEY (user_id,role_id),
     FOREIGN KEY (user_id) REFERENCES users (id)
     ON delete NO ACTION ON update NO ACTION,
     FOREIGN KEY (role_id)  REFERENCES roles (id)
     ON delete NO ACTION ON update NO ACTION
);

DROP TABLE IF EXISTS properties CASCADE;
CREATE TABLE properties
(
    id         BIGSERIAL PRIMARY KEY,
    category_id smallserial NOT NULL,
    user_id    BIGSERIAL NOT NULL,
    adress_id  BIGSERIAL NOT NULL,
    area       numeric(10, 2),
    price       MONEY,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP

);

DROP TABLE IF EXISTS adress CASCADE;
CREATE TABLE adress(

   id         BIGSERIAL PRIMARY KEY,
   location    VARCHAR(50)  NOT NULL,
   description VARCHAR(100)

);

DROP TABLE IF EXISTS adress CASCADE;
CREATE TABLE images(
   id         BIGSERIAL PRIMARY KEY,
   property_id BIGSERIAL,
   title    VARCHAR(50),
   photo TEXT NOT NULL

);
DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
   id   smallserial PRIMARY KEY,
    title VARCHAR(50) NOT NULL
);
