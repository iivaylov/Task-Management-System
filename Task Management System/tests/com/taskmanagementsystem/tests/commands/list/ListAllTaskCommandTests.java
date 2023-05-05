package com.taskmanagementsystem.tests.commands.list;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.list.ListAllTasksCommand;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

public class ListAllTaskCommandTests {

    private Command listAllTaskCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    private List<Task> tasks;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        listAllTaskCommand = new ListAllTasksCommand(taskManagementSystemRepository);
        tasks = taskManagementSystemRepository.getTasks();
    }

    @Test
    public void execute_ShouldReturnCorrectMessage_WhenThereAreNoTasksInTheSystem() {
        //Arrange
        List<String> parameters = getList(0);
        //Act
        String actual = listAllTaskCommand.execute(parameters);
        //Assert
        Assertions.assertEquals("There are no tasks in the system.", actual);
    }
}
