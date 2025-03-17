package com.devbp.tasks.services.impl;

import com.devbp.tasks.controllers.TaskListController;
import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.mappers.TaskListMapper;
import com.devbp.tasks.repositories.TaskListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.devbp.tasks.controllers.TaskListController.TASK_LIST_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class TaskListServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskListController taskListController;

    @Autowired
    private TaskListMapper taskListMapper;





    @Test
    public void testTaskListReturnListOfTasks() throws Exception {

        mockMvc.perform(get(TASK_LIST_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

}