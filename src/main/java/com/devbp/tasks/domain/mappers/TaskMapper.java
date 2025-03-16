package com.devbp.tasks.domain.mappers;

import com.devbp.tasks.domain.dto.TaskDto;
import com.devbp.tasks.domain.entities.Task;

public interface TaskMapper {

    Task mapFrom( TaskDto taskDto);

    TaskDto mapTo( Task task);
}
