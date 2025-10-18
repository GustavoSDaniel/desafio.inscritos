package dev.matheuslf.desafio.inscritos.task;

import dev.matheuslf.desafio.inscritos.project.Project;
import dev.matheuslf.desafio.inscritos.project.ProjectNotFoundException;
import dev.matheuslf.desafio.inscritos.project.ProjectRepository;
import dev.matheuslf.desafio.inscritos.util.DateEndBeforeStarDateException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;
    private final static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) throws TaskNameTooShortException {

        if (taskRequest.title().length() < 5) {
            throw new TaskNameTooShortException();
        }

        if (taskRepository.existsByTitle(taskRequest.title())) {
            throw new TaskNameTooShortException();
        }

        Task newTask = new Task();
        newTask.setTitle(taskRequest.title());
        newTask.setDescription(taskRequest.description());
        newTask.setStatus(StatusTask.TODO);
        newTask.setPriority(taskRequest.priority());
        newTask.setDueDate(taskRequest.dueDate());

        Task salvedTask = taskRepository.save(newTask);

        log.info("Task criada com sucesso {}", salvedTask.getTitle());

        return taskMapper.toTaskResponse(salvedTask);

    }

    @Override
    @Transactional
    public TaskResponseWhitProject createTaskInTheProject(TaskRequestWhitProject taskRequest)
            throws TaskNameTooShortException {

        log.info("Iniciando criação de task {} para o projeto {}", taskRequest, taskRequest.projectId());

        if (taskRequest.title().length() < 5) {
            throw new TaskNameTooShortException();
        }

        if (taskRepository.existsByTitleAndProjectId(taskRequest.title(), taskRequest.projectId())) {

            throw new TaskNameInTheProjectDuplicateException();
        }

        if (taskRequest.dueDate().isBefore(LocalDateTime.now())){
            throw new DateEndBeforeStarDateException();
        }

        Project project = projectRepository.findById(taskRequest.projectId())
                .orElseThrow(ProjectNotFoundException::new);

       Task newTask = new Task();
       newTask.setTitle(taskRequest.title());
       newTask.setDescription(taskRequest.description());
       newTask.setStatus(StatusTask.TODO);
       newTask.setPriority(taskRequest.priority());
       newTask.setDueDate(taskRequest.dueDate());
       newTask.setProject(project);

       Task salvedTask = taskRepository.save(newTask);

        log.info("Task do Projeto {} criada com sucesso {}", newTask.getProject(), salvedTask.getTitle());

       return taskMapper.toTaskResponseWhitProject(salvedTask);
    }

    @Override
    public void deleteTask(Long Id ) {

        Task task = taskRepository.findById(Id).orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }


}
