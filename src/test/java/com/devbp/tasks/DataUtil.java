package com.devbp.tasks;

import com.devbp.tasks.domain.entities.TaskList;

public class DataUtil {

    TaskList taskList1 = TaskList.builder()
            .title("Task List 1")
            .description("Example task list")
            .tasks(null)
            .build();

    TaskList  taskList2 = TaskList.builder()
            .title("Task List 2")
            .description("Example task list 2")
            .tasks(null)
            .build();
}
