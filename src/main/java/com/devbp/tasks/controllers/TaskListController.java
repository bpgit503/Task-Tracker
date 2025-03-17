package com.devbp.tasks.controllers;

import com.devbp.tasks.domain.dto.TaskListDto;
import com.devbp.tasks.mappers.TaskListMapper;
import com.devbp.tasks.services.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TaskListController {

    public static final String TASK_LIST_PATH = "/tasklist";

    public static final String TASK_LIST_PATH_ID = "/tasklist/{id}";


    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    @GetMapping(TASK_LIST_PATH)
    public ResponseEntity<List<TaskListDto>> listTaskList(){

        List<TaskListDto> list =  taskListService.listTaskList().stream()
                .map(taskListMapper::toDto)
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);

    }


}
