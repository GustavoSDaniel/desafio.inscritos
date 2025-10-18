package dev.matheuslf.desafio.inscritos.project;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

        @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchName, '%'))")
        List<Project> searchByName(@Param("searchName") String name);

        Optional<Project> findByName(String name);

        Boolean existsByName(String name);

}
