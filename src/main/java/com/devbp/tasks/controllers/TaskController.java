package com.devbp.tasks.controllers;

import com.devbp.tasks.domain.dto.TaskDto;
import com.devbp.tasks.mappers.TaskMapper;
import com.devbp.tasks.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TaskController {

    public static final String TASK_PATH = "/tasks";

    public static final String TASK_PATH_ID = "/tasks/{taskListId}";

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    @GetMapping(TASK_PATH_ID)
    public List<TaskDto> listTasks(@PathVariable UUID taskListId) {
        return taskService.listTask(taskListId).stream().map(taskMapper::toDto).toList();
    }

    @PostMapping(TASK_PATH_ID)
    public TaskDto createTask(@PathVariable UUID taskListId, @RequestBody TaskDto taskDto) {
        return taskMapper.toDto(taskService.createTask(taskListId, taskMapper.fromDto(taskDto)));

    }

    @GetMapping(TASK_PATH_ID+"/{taskId}")
    public Optional<TaskDto> getTask(@PathVariable UUID taskListId, @PathVariable UUID taskId) {
        return taskService.getTask(taskListId, taskId).map(taskMapper::toDto);

    }
}
