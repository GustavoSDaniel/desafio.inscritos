package dev.matheuslf.desafio.inscritos.task;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toTask(TaskRequest taskRequest){
        if (taskRequest == null) return null;

        return new Task(

                taskRequest.title(),
                taskRequest.description(),
                taskRequest.priority(),
                taskRequest.dueDate()
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
                task.getDueDate(),
                task.getProject() != null ? task.getProject().getName() : null,
                task.getUser() != null ? task.getUser().getUsername() : null
        );

    }


}
