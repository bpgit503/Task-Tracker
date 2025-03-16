package com.devbp.tasks.domain.mappers.impl;

import com.devbp.tasks.domain.dto.TaskDto;
import com.devbp.tasks.domain.entities.Task;
import com.devbp.tasks.domain.mappers.TaskMapper;

public class TaskMapperImpl implements TaskMapper {
    @Override
    public TaskDto mapTo(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }

    @Override
    public Task mapFrom(TaskDto taskDto) {
        return new  Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.dueDate(),
                taskDto.priority(),
                taskDto.status(),
                null,
                null,
                null
        );
    }
}
