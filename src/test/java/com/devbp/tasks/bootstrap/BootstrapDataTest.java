package com.devbp.tasks.bootstrap;

import com.devbp.tasks.repositories.TaskListRepository;
import com.devbp.tasks.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BootstrapDataTest {

    @Autowired
    TaskListRepository taskListRepository;

    @Autowired
    TaskRepository taskRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(taskListRepository, taskRepository);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run();

        assertThat(taskRepository.count()).isEqualTo(3);
        assertThat(taskListRepository.count()).isEqualTo(2);
        assertThat((long) taskListRepository.findAll().get(0).getTasks().size()).isEqualTo(2);
    }
}