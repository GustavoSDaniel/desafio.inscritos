CREATE TABLE users (
                       id UUID NOT NULL PRIMARY KEY,
                       user_name VARCHAR(100) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP
);