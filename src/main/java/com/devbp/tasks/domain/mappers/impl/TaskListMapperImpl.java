package com.devbp.tasks.domain.mappers.impl;

import com.devbp.tasks.domain.dto.TaskListDto;
import com.devbp.tasks.domain.entities.Task;
import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.domain.entities.TaskStatus;
import com.devbp.tasks.domain.mappers.TaskListMapper;
import com.devbp.tasks.domain.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks ->
                                tasks.stream().map(taskMapper::fromDto).toList()
                        ).orElse(null)
        );

    }

    private Double calculateTaskListProgress(List<Task> tasks) {

        if (null == tasks) {
            return null;
        }

        long closedTaskCount = tasks.stream().filter(task ->
                TaskStatus.CLOSED == task.getStatus()).count();

        return (double) closedTaskCount / tasks.size();

    }

    @Override
    public TaskList fromDto(TaskList taskList) {
        return null;
    }
}
