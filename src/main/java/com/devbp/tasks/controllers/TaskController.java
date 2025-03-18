package com.devbp.tasks.controllers;

import com.devbp.tasks.domain.dto.TaskDto;
import com.devbp.tasks.mappers.TaskMapper;
import com.devbp.tasks.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TaskController {

    public static final String TASK_PATH = "/tasks";

    public static final String TASK_PATH_ID = "/tasks/{id}";

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    @GetMapping(TASK_PATH)
    public List<TaskDto> listTasks(){
        return taskService.listTask().stream().map(taskMapper::toDto).collect(Collectors.toList());
    }
}
