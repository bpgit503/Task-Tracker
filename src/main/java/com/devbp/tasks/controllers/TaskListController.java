package com.devbp.tasks.controllers;

import com.devbp.tasks.domain.dto.TaskListDto;
import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.mappers.TaskListMapper;
import com.devbp.tasks.services.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskListController {

    public static final String TASK_LIST_PATH = "/tasklist";

    public static final String TASK_LIST_PATH_ID = "/tasklist/{id}";


    private final TaskListService taskListService;

    private final TaskListMapper taskListMapper;

    @GetMapping(TASK_LIST_PATH)
    public List<TaskListDto> listTaskList() {

        return taskListService.listTaskList().stream()
                .map(taskListMapper::toDto)
                .toList();

    }

    @PostMapping(TASK_LIST_PATH)
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
        return taskListMapper.toDto(taskListService.createTaskList(taskListMapper.fromDto(taskListDto)));
    }

    @GetMapping(TASK_LIST_PATH_ID)
    public Optional<TaskListDto> getTaskList(@PathVariable UUID id) {

        return taskListService.getTaskList(id).map(taskListMapper::toDto);
    }

    @PutMapping(TASK_LIST_PATH_ID)
    public TaskListDto updateTaskList( @PathVariable UUID id ,@RequestBody TaskListDto taskListDto) {

        return taskListMapper.toDto(taskListService.updateTaskList(id, taskListMapper.fromDto(taskListDto)));

    }

    @DeleteMapping(TASK_LIST_PATH_ID)
    public void deleteTaskList(@PathVariable UUID id) {
        taskListService.deleteTaskList(id);
    }

}
