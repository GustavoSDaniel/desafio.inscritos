package dev.matheuslf.desafio.inscritos.task;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Boolean existsByTitleAndProjectId(String title, Long idProject);


    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTitle, '%'))")
    List<Task> findByTitle(String title);

    List<Task> findByProjectId(Long idProject);
}
