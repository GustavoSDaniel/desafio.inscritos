CREATE SEQUENCE project_seq  START WITH 1 INCREMENT BY 50;

CREATE TABLE project (
                         id BIGINT NOT NULL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description VARCHAR(255),
                         start_date TIMESTAMP NOT NULL,
                         update_at TIMESTAMP,
                         end_date TIMESTAMP,
                         user_id UUID NOT NULL,

CONSTRAINT fk_projects_on_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_projects_on_name ON project(name);
CREATE INDEX idx_projects_on_user_id ON project(user_id);



