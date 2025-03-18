package com.devbp.tasks.services.impl;

import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.repositories.TaskListRepository;
import com.devbp.tasks.services.TaskListService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;


    @Override
    @Transactional(readOnly = true)
    public List<TaskList> listTaskList() {

        List<TaskList> tasklist = taskListRepository.findAll();

        tasklist.forEach(taskList -> Hibernate.initialize(taskList.getTasks()));

        return tasklist;
    }
}
