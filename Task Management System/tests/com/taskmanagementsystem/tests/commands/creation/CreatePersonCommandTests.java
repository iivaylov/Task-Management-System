package com.taskmanagementsystem.tests.commands.creation;


import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.creation.CreatePersonCommand;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

/**
 * CreatePersonCommand arguments: {{Name}}
 */
public class CreatePersonCommandTests {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private Command createPersonCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        createPersonCommand = new CreatePersonCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid number of arguments are passed")
    public void execute_ShouldThrowException_WhenInvalidNumberOfArgumentsArePassed() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        Assertions.assertThrows(InvalidUserInputException.class, () -> createPersonCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should return correct message when person is created")
    public void execute_ShouldReturnCorrectMessage_WhenValidNumberOfArgumentsArePassed() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);

        parameters.set(0, "Jonny");

        String actualResult = createPersonCommand.execute(parameters);
        String expectedResult = String.format("Person with name %s was created successfully.", parameters.get(0));

        Assertions.assertEquals(expectedResult, actualResult);
    }
}
