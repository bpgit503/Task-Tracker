package com.devbp.tasks.services;

import com.devbp.tasks.domain.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<Task> listTask(UUID taskListId);

    Task createTask(UUID taskListId, Task task);

//    Task findByTaskListIdAndId(UUID taskListId, UUID id);


}
