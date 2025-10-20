CREATE SEQUENCE project_seq  START WITH 1 INCREMENT BY 50;

CREATE TABLE project (
                         id BIGINT NOT NULL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description VARCHAR(255),
                         start_date TIMESTAMP NOT NULL,
                         update_at TIMESTAMP,
                         end_date TIMESTAMP
);

CREATE INDEX idx_project_name ON project(name);



