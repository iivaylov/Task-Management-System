package com.taskmanagementsystem.tests.commands.change.feedback;

import com.taskmanagementsystem.commands.change.feedback.ChangeStatusOfFeedbackCommand;
import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.ElementNotFoundException;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

/**
 * ChangeStatusOfFeedbackCommand arguments: {{Id}}
 */
public class ChangeStatusOfFeedbackCommandTest {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private Command changeStatusOfFeedbackCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        changeStatusOfFeedbackCommand = new ChangeStatusOfFeedbackCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when arguments are less than expected")
    public void execute_ShouldThrowException_WhenInvalidParametersCount() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> changeStatusOfFeedbackCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when arguments are more than expected")
    public void execute_ShouldThrowException_WhenInvalidParametersCount2() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> changeStatusOfFeedbackCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when feedback with given id does not exist")
    public void execute_ShouldThrowException_WhenFeedbackIsNotFound() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Feedback feedback = taskManagementSystemRepository.createFeedback(team,
                board, "VeryLongTitle", "Description", 5);
        board.addFeedback(feedback);

        parameters.set(0, String.valueOf(feedback.getId() + 1));

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class,
                () -> changeStatusOfFeedbackCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should change status of feedback with given id")
    public void execute_ShouldChangeStatusOfFeedback_WhenValidParametersArePassed() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Feedback feedback = taskManagementSystemRepository.createFeedback(team,
                board, "VeryLongTitle", "Description", 5);
        board.addFeedback(feedback);

        parameters.set(0, String.valueOf(feedback.getId()));

        // Act
        String result = changeStatusOfFeedbackCommand.execute(parameters);

        // Assert
        Assertions.assertEquals(String.format("Status of feedback with ID %d was changed to %s.",
                feedback.getId(), feedback.getStatus()), result);
    }


}
