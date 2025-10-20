CREATE SEQUENCE task_seq  START WITH 1 INCREMENT BY 50;

CREATE TABLE task (
                       id                  BIGSERIAL PRIMARY KEY,
                       title               VARCHAR(150) NOT NULL,
                       description         TEXT NOT NULL,
                       status              VARCHAR(50),
                       priority            VARCHAR(50),
                       created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at          TIMESTAMP,
                       due_date            TIMESTAMP NOT NULL,
                       project_id          BIGINT NOT NULL,

                       CONSTRAINT fk_tasks_project
                           FOREIGN KEY (project_id)
                               REFERENCES project(id)
                               ON DELETE CASCADE
);

CREATE INDEX idx_tasks_project_id ON task(project_id);
CREATE INDEX idx_tasks_status ON task(status);
CREATE INDEX idx_tasks_priority ON task(priority);
