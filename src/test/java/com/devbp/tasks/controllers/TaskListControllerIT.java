package com.devbp.tasks.controllers;

import com.devbp.tasks.domain.dto.TaskListDto;
import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.repositories.TaskListRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devbp.tasks.controllers.TaskListController.TASK_LIST_PATH;
import static com.devbp.tasks.controllers.TaskListController.TASK_LIST_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class TaskListControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListTaskList() throws Exception {
        List<TaskList> taskLists = taskListRepository.findAll();

        mockMvc.perform(get(TASK_LIST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].title", hasItems("Todo List", "Project X")));


        assertThat(taskLists).isNotNull();
        assertThat(taskLists).hasSize(2);

    }

    @Test
    void testCreateNewTaskList() throws Exception {
        TaskListDto taskListDto = new TaskListDto(
                null,
                "Test Title",
                "",
                0,
                0.0,
                null
        );

        MvcResult result = mockMvc.perform(post(TASK_LIST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskListDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(taskListDto.title()))
                .andReturn();

        TaskListDto createdDto = objectMapper.readValue(result.getResponse().getContentAsString(), TaskListDto.class);

        Optional<TaskList> savedTaskList = taskListRepository.findById(createdDto.id());
        assertThat(savedTaskList).isPresent();
        assertThat(savedTaskList.get().getTitle()).isEqualTo(taskListDto.title());


    }

    @Test
    void testGetTaskList() throws Exception {
        UUID taskListId = taskListRepository.findAll().get(0).getId();

        MvcResult result = mockMvc.perform(get(TASK_LIST_PATH_ID, taskListId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        TaskListDto retrieveDto = objectMapper.readValue(result.getResponse().getContentAsString(), TaskListDto.class);

        Optional<TaskList> savedTaskList = taskListRepository.findById(retrieveDto.id());
        assertThat(savedTaskList).isPresent();
        assertThat(savedTaskList.get().getTitle()).isEqualTo(retrieveDto.title());


    }
}