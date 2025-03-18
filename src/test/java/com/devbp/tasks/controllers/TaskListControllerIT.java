package com.devbp.tasks.controllers;

import com.devbp.tasks.domain.dto.TaskListDto;
import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.mappers.TaskListMapper;
import com.devbp.tasks.repositories.TaskListRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
class TaskListControllerIT {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskListController taskListController;

    @Autowired
    private TaskListMapper  taskListMapper;

    @Test
    void testRepositoryLog() {
       List<TaskListDto> dto = taskListController.listTaskList();
        log.info(dto.toString());
    }

    @Test
    void testCreatSaveNewTaskList() {
        TaskList taskList = taskListRepository.findAll().get(0);
        taskList.setId(null);
        taskList.setTitle("New Task List");
        TaskListDto taskListDto = taskListMapper.toDto(taskList);

        TaskListDto newTaskList = taskListController.createTaskList(taskListDto);

        assertThat(newTaskList.id()).isNotNull();

    }
}