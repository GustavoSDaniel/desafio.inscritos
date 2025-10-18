package dev.matheuslf.desafio.inscritos.task;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponseWhitProject toTaskResponseWhitProject(Task task) {

        if (task == null){
            return null;
        }

        return new TaskResponseWhitProject(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getProject() != null ? task.getProject().getName() : null
        );

    }

    public TaskResponse toTaskResponse(Task task) {

        if (task == null){
            return null;
        }

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );

    }


}
