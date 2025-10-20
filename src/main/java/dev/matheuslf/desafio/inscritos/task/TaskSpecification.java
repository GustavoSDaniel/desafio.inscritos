package dev.matheuslf.desafio.inscritos.task;

import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> byTitle(String title) {

        return (root, query, criteriaBuilder) -> {
            if (title == null || title.trim().isEmpty()) {
                return null;
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
                    );
        };

    }

    public static Specification<Task> byProjectId(Long projectId) {
        return (root, query, criteriaBuilder) -> {
            if (projectId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("project").get("id"), projectId);
        };

    }

    public static Specification<Task> byStatus(StatusTask status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Task> byPriority(PriorityTask priority) {
        return (root, query, criteriaBuilder) ->  {
            if (priority == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("priority"), priority);
        };

    }

    public static Specification<Task> filters(
            String title, Long projectId, StatusTask status, PriorityTask priority) {
        return Specification.allOf(
                byTitle(title),
                byProjectId(projectId),
                byStatus(status),
                byPriority(priority)
        );
    }

}


