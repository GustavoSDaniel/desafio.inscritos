package dev.matheuslf.desafio.inscritos.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest)
            throws TaskNameTooShortException;

    Page<TaskResponse> getAllTask(Pageable pageable);

    TaskResponse getTaskById(Long id);

    List<TaskResponse> searchByTaskTitle(String title);

    List<TaskResponse> findTaskByProjectId(Long idProject);

    TaskResponse updateTask(Long id, TaskRequestUpdate taskRequestUpdate);

    void deleteTask(Long Id);

}
