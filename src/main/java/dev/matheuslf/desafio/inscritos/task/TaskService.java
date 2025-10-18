package dev.matheuslf.desafio.inscritos.task;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest)
            throws TaskNameTooShortException;

    TaskResponse updateTask(Long id, RequestUpdate taskRequestUpdate);

    void deleteTask(Long Id);

}
