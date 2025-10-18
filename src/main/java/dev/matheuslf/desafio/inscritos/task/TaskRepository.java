package dev.matheuslf.desafio.inscritos.task;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Boolean existsByTitleAndProjectId(String title, Long idProject);
}
