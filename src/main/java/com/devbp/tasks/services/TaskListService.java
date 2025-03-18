package com.devbp.tasks.services;

import com.devbp.tasks.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List<TaskList> listTaskList();

    TaskList createTaskList(TaskList taskList);

    Optional<TaskList> getTaskList(UUID taskListId);


}
