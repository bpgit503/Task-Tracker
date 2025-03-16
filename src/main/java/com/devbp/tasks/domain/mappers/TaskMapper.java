package com.devbp.tasks.domain.mappers;

import com.devbp.tasks.domain.dto.TaskDto;
import com.devbp.tasks.domain.entities.Task;
import org.springframework.stereotype.Component;


public interface TaskMapper {

    Task toDto(TaskDto taskDto);

    TaskDto fromDto(Task task);
}
