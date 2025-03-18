package com.devbp.tasks.bootstrap;

import com.devbp.tasks.domain.entities.Task;
import com.devbp.tasks.domain.entities.TaskList;
import com.devbp.tasks.domain.entities.TaskPriority;
import com.devbp.tasks.domain.entities.TaskStatus;
import com.devbp.tasks.repositories.TaskListRepository;
import com.devbp.tasks.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData  implements CommandLineRunner {

    private final TaskListRepository taskListRepository;

    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        loadTask();

    }

    private void loadTask() {

        if(taskListRepository.count() == 0) {
            TaskList taskList1 = TaskList.builder()
                    .title("Todo List")
                    .description("List of things I need to do")
                    .tasks(null)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            TaskList  taskList2 = TaskList.builder()
                    .title("Project X")
                    .description("Course and projects to complete")
                    .tasks(null)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            taskListRepository.save(taskList1);
            taskListRepository.save(taskList2);


            Task task1 = Task.builder()
                    .title("Do laundry")
                    .description("Do laundry")
                    .dueDate(LocalDateTime.of(2025,3,20,20,0))
                    .status(TaskStatus.OPEN)
                    .priority(TaskPriority.HIGH)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Task task2 = Task.builder()
                    .title("Make Kombucha")
                    .description("Make Kombucha for the family")
                    .dueDate(LocalDateTime.of(2025,3,17,20,0))
                    .status(TaskStatus.CLOSED)
                    .priority(TaskPriority.MEDIUM)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Task task3 = Task.builder()
                    .title("Complete Spring Docs Course")
                    .description("Complete All Important sections of the Spring doc course")
                    .dueDate(LocalDateTime.of(2025,3,30,20,0))
                    .status(TaskStatus.OPEN)
                    .priority(TaskPriority.HIGH)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            taskRepository.save(task1);
            taskRepository.save(task2);
            taskRepository.save(task3);


        taskList1.setTasks(List.of(task1,task2));
        taskList2.setTasks(List.of(task3));

    }


    }
}
