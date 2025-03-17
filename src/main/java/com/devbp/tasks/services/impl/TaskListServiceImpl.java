package com.devbp.tasks.services.impl;

import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.repositories.TaskListRepository;
import com.devbp.tasks.services.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;


    @Override
    public List<TaskList> listTaskList() {
        return taskListRepository.findAll();
    }
}
