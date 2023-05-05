package com.taskmanagementsystem.tests.commands.show;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.show.ShowAllPeopleCommand;
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
 * ShowAllPeopleCommand arguments: None
 */

public class ShowAllPeopleCommandTests {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    private Command showAllPeopleCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        showAllPeopleCommand = new ShowAllPeopleCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid number of arguments are passed")
    public void execute_ShouldThrowException_WhenInvalidNumberOfArgumentsArePassed() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> showAllPeopleCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should return correct message when no people are in the system")
    public void execute_ShouldReturnCorrectMessage_WhenNoPeopleAreInTheSystem() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);

        // Act
        String actualResult = showAllPeopleCommand.execute(parameters);

        String expectedResult = "---People---" + System.lineSeparator() +
                "There are no people in the system." + System.lineSeparator() +
                "###################################";
        // Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("execute() should return correct message when people are in the system")
    public void execute_ShouldReturnCorrectMessage_WhenPeopleAreInTheSystem() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        taskManagementSystemRepository.createMember("Matthew");

        // Act
        String actualResult = showAllPeopleCommand.execute(parameters);

        String expectedResult = "---People---" +
                System.lineSeparator() +
                "1. Matthew";
        // Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
