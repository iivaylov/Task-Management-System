package com.taskmanagementsystem.tests.commands.showactivity;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.showactivity.ShowTeamActivityCommand;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.ElementNotFoundException;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

/**
 * ShowTeamActivityCommand arguments: {{TeamName}}
 */
public class ShowTeamActivityCommandTests {
    private static final int EXPECTED_NUMBER_OF_STORIES = 1;

    private Command showTeamActivityCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        showTeamActivityCommand = new ShowTeamActivityCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when argument count is different than expected")
    public void execute_ShouldThrowException_WhenArgumentCountDifferentThanExpected() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_STORIES + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> showTeamActivityCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when team is not found")
    public void execute_ShouldThrowException_WhenTeamDoesNotExist() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_STORIES);
        parameters.set(0, "TeamOne");

        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class,
                () -> showTeamActivityCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should return correct message when team is found")
    public void execute_ShouldReturnCorrectMessage_WhenTeamExists() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_STORIES);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        parameters.set(0, team.getName());

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> showTeamActivityCommand.execute(parameters));
    }
}
