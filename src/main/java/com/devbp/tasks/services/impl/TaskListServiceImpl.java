package com.devbp.tasks.services.impl;

import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.repositories.TaskListRepository;
import com.devbp.tasks.services.TaskListService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(null != taskList.getId()) {
            throw new IllegalArgumentException("Task list already has an ID!");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Task list title must not be empty!");
        }

        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID taskListId) {
        return taskListRepository.findById(taskListId);
    }
}
