package com.devbp.tasks.services.impl;

import com.devbp.tasks.domain.entities.Task;
import com.devbp.tasks.repositories.TaskRepository;
import com.devbp.tasks.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> listTask() {
        return taskRepository.findAll();
    }
}
