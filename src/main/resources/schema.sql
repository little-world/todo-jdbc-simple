DROP TABLE IF EXISTS todo;

CREATE TABLE todo (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    task       VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS users;

CREATE TABLE users (
                      id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name       VARCHAR(255) NOT NULL
);
