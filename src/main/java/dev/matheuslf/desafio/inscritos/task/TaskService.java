package dev.matheuslf.desafio.inscritos.task;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest) throws TaskNameTooShortException;

    TaskResponseWhitProject createTaskInTheProject(TaskRequestWhitProject taskRequest)
            throws TaskNameTooShortException;

    void deleteTask(Long Id);

}
