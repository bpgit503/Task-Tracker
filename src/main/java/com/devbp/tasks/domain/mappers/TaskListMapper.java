package com.devbp.tasks.domain.mappers;

import com.devbp.tasks.domain.dto.TaskListDto;
import com.devbp.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskListDto toDto(TaskList taskList);

    TaskList fromDto(TaskListDto taskList);

}
