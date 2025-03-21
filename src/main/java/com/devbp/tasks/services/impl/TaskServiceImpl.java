package com.devbp.tasks.services.impl;

import com.devbp.tasks.domain.entities.Task;
import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.domain.entities.TaskPriority;
import com.devbp.tasks.domain.entities.TaskStatus;
import com.devbp.tasks.repositories.TaskListRepository;
import com.devbp.tasks.repositories.TaskRepository;
import com.devbp.tasks.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;


    @Override
    public List<Task> listTask(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already has an ID");
        }

        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title!");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task List ID provided!"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskPriority,
                taskStatus,
                taskList,
                now,
                now
        );

        return taskRepository.save(taskToSave);

    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        if (!taskRepository.existsByTaskListIdAndId(taskListId, taskId)) {
            throw new IllegalArgumentException("Invalid Task List ID or Task ID provided!");
        }
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {

        if (null == task.getId()) {
            throw new IllegalArgumentException("Task must have an ID");
        }

        if (!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task IDs do not match!");
        }

        if (null == task.getPriority()) {
            throw new IllegalArgumentException("Task must have a valid priority");
        }

        if (null == task.getStatus()) {
            throw new IllegalArgumentException("Task must have a valid status");
        }


        return taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .map(exsitingTask -> {
                            exsitingTask.setTitle(task.getTitle());
                            exsitingTask.setDescription(task.getDescription());
                            exsitingTask.setDueDate(task.getDueDate());
                            exsitingTask.setPriority(task.getPriority());
                            exsitingTask.setStatus(task.getStatus());
                            exsitingTask.setUpdatedAt(LocalDateTime.now());
                            return taskRepository.save(exsitingTask);
                        }

                )
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task List ID or Task ID provided!"));


    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
