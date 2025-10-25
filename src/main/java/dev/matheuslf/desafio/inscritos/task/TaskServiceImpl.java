package dev.matheuslf.desafio.inscritos.task;

import dev.matheuslf.desafio.inscritos.project.Project;
import dev.matheuslf.desafio.inscritos.project.ProjectNotFoundException;
import dev.matheuslf.desafio.inscritos.project.ProjectRepository;
import dev.matheuslf.desafio.inscritos.user.User;
import dev.matheuslf.desafio.inscritos.user.UserNotFoundException;
import dev.matheuslf.desafio.inscritos.user.UserRepository;
import dev.matheuslf.desafio.inscritos.util.DateEndBeforeStarDateException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest)
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

        User user = userRepository.findById(taskRequest.idUser())
                .orElseThrow(UserNotFoundException::new);

        Project project = projectRepository.findById(taskRequest.projectId())
                .orElseThrow(ProjectNotFoundException::new);

       Task newTask = new Task();
       newTask.setTitle(taskRequest.title());
       newTask.setDescription(taskRequest.description());
       newTask.setStatus(StatusTask.TODO);
       newTask.setPriority(taskRequest.priority());
       newTask.setDueDate(taskRequest.dueDate());
       newTask.setUser(user);
       newTask.setProject(project);

       Task salvedTask = taskRepository.save(newTask);

        log.info("Task do Projeto {} criada com sucesso {}", newTask.getProject(), salvedTask.getTitle());

       return taskMapper.toTaskResponse(salvedTask);
    }

    @Override
    public Page<TaskResponse> getAllTask(Pageable pageable) {

        Page<Task> allTask = taskRepository.findAll(pageable);

        if (allTask.isEmpty()) {
            log.debug("Nenhuma Task encontrada");

            return Page.empty(pageable);
        }

        log.debug("Retornando todas as tasks no total de {} ",allTask.getTotalElements());

        return allTask.map(taskMapper::toTaskResponse);
    }

    @Override
    public TaskResponse getTaskById(Long id) {

        log.debug("Buscando tak por ID {} ", id);

        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        log.debug("Task encontrada com sucesso ID  {} ", id);
        return taskMapper.toTaskResponse(task);

    }

    @Override
    public Page<TaskResponse> findTasksWithFilters(
            String title, Long projectId, StatusTask status, PriorityTask priority, Pageable pageable) {

        log.debug("Buscando tarefas com filtros: title {}, projectId {}, status {}, priority {}",
                title, projectId, status, priority);

        Specification<Task> specification = TaskSpecification.filters(title, projectId, status, priority);

        Page<Task> tasks = taskRepository.findAll(specification, pageable);

        return tasks.map(taskMapper::toTaskResponse);

    }


    @Override
    @Transactional
    public TaskResponse updateTask(Long id, TaskRequestUpdate taskRequestUpdate) {

        log.info("Atualizando status da task ID: {}", id);

        Task taskExist = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (taskExist.getStatus() == StatusTask.DONE) {

            throw new TaskStatusDoneException();
        }

        taskExist.setStatus(taskRequestUpdate.statusTask());

        Task taskUpdated = taskRepository.save(taskExist);

        log.info("Task com o Id: {} atualizado com sucesso para o status de {}",
                taskUpdated.getId(), taskUpdated.getStatus());

        return taskMapper.toTaskResponse(taskUpdated);
    }


    @Override
    public void deleteTask(Long Id ) {

        Task task = taskRepository.findById(Id).orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }


}
