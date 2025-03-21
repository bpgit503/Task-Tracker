package com.devbp.tasks.controllers;

import com.devbp.tasks.domain.dto.TaskDto;
import com.devbp.tasks.domain.entities.Task;
import com.devbp.tasks.domain.entities.TaskPriority;
import com.devbp.tasks.domain.entities.TaskStatus;
import com.devbp.tasks.repositories.TaskListRepository;
import com.devbp.tasks.repositories.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.devbp.tasks.controllers.TaskController.TASK_PATH_ID;
import static com.devbp.tasks.controllers.TaskController.TASK_PATH_TASK_LIST_ID_TASK_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListTaskList() throws Exception {
        UUID taskListId = taskListRepository.findAll().get(0).getId();

        UUID firstTaskId = taskRepository.findAll().get(0).getId();
        UUID secondTaskId = taskRepository.findAll().get(1).getId();

        String results = mockMvc.perform(get(TASK_PATH_ID, taskListId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn().getResponse().getContentAsString();
        results = results.substring(results.indexOf("["));

        log.info(results);

        assertThat(results).isNotEmpty();
        assertThat(results).contains(firstTaskId.toString());
        assertThat(results).contains(secondTaskId.toString());
    }

    @Test
    void testCreateNewTask() throws Exception {
        UUID taskListId = taskListRepository.findAll().get(1).getId();
        TaskDto newTask = new TaskDto(
                null,
                "New Task Title",
                "New Task Description",
                LocalDateTime.of(2025, 3, 27, 0, 0, 0),
                TaskPriority.HIGH,
                TaskStatus.OPEN
        );

        MvcResult result = mockMvc.perform(post(TASK_PATH_ID, taskListId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("New Task Title"))
                .andExpect(jsonPath("$.description").value("New Task Description"))
                .andReturn();

        TaskDto createdTaskDto = objectMapper.readValue(result.getResponse().getContentAsString(), TaskDto.class);
        Optional<Task> savedTask = taskRepository.findByTaskListIdAndId(taskListId, createdTaskDto.id());

        assertThat(savedTask).isPresent();
        assertThat(savedTask.get().getTitle()).isEqualTo(newTask.title());

    }

    @Test
    void testGetTask() throws Exception {
        UUID taskListId = taskListRepository.findAll().get(0).getId();
        Task task = taskRepository.findAll().get(0);

        MvcResult result = mockMvc.perform(get(TASK_PATH_TASK_LIST_ID_TASK_ID, taskListId, task.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(task.getId().toString()))
                .andReturn();

        TaskDto retrievedDto = objectMapper.readValue(result.getResponse().getContentAsString(), TaskDto.class);
        Optional<Task> retrievedTask = taskRepository.findByTaskListIdAndId(taskListId, retrievedDto.id());

        assertThat(retrievedTask).isPresent();
        assertThat(retrievedTask.get().getTitle()).isEqualTo(retrievedDto.title());
        assertThat(retrievedTask.get().getDescription()).isEqualTo(retrievedDto.description());
    }

    @Test
    void testTaskNotFound() throws Exception {
        UUID taskListId = taskListRepository.findAll().get(0).getId();
        UUID taskId = taskRepository.findAll().get(0).getId();

        mockMvc.perform(get(TASK_PATH_TASK_LIST_ID_TASK_ID, taskListId, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {

                    String responseBody = result.getResponse().getContentAsString();
                    assertThat(responseBody).contains("Invalid Task List ID or Task ID provided!");

                });


    }
}