package com.devbp.tasks.services.impl;

import com.devbp.tasks.controllers.TaskListController;
import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.mappers.TaskListMapper;
import com.devbp.tasks.repositories.TaskListRepository;
import com.devbp.tasks.services.TaskListService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;

import static com.devbp.tasks.controllers.TaskListController.TASK_LIST_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(TaskListController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskListServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskListRepository taskListRepository;

    @MockitoBean
    private TaskListService taskListService;

    @MockitoBean
    private TaskListMapper taskListMapper;




    @Test
    public void testTaskListReturnListOfTasks() throws Exception {
        List<TaskList> taskLists = Arrays.asList(
                new TaskList(),
                new TaskList()
        );
        log.info(taskListRepository.findAll().toString());


        given(taskListService.listTaskList()).willReturn(taskLists);

        mockMvc.perform(get(TASK_LIST_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));


    }

}