package com.devbp.tasks.controllers;

import com.devbp.tasks.domain.dto.TaskListDto;
import com.devbp.tasks.mappers.TaskListMapper;
import com.devbp.tasks.services.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
