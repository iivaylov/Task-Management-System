package com.taskmanagementsystem.tests.commands.change.feedback;

import com.taskmanagementsystem.commands.change.feedback.ChangeRatingOfFeedbackCommand;
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
 * ChangeRatingOfFeedbackCommand arguments: {{Id}} {{Rating}}
 */
public class ChangeRatingOfFeedbackCommandTest {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private Command changeRatingOfFeedbackCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        changeRatingOfFeedbackCommand = new ChangeRatingOfFeedbackCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid parameters count")
    public void execute_ShouldThrowException_WhenInvalidParametersCount() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> changeRatingOfFeedbackCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when rating is not valid")
    public void execute_ShouldThrowException_WhenRatingIsNotValid() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        parameters.set(1, "invalidRating");

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> changeRatingOfFeedbackCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when feedback is not found")
    public void execute_ShouldThrowException_WhenFeedBackDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Feedback feedback = taskManagementSystemRepository.createFeedback(team,
                board, "VeryLongTitle", "Description", 5);
        board.addFeedback(feedback);

        parameters.set(0, String.valueOf(feedback.getId() + 1));
        parameters.set(1, "5");

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class,
                () -> changeRatingOfFeedbackCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should not change rating when rating is the same")
    public void execute_ShouldNotChangeRating_WhenRatingIsTheSame() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Feedback feedback = taskManagementSystemRepository.createFeedback(team,
                board, "VeryLongTitle", "Description", 5);
        board.addFeedback(feedback);

        parameters.set(0, "1");
        parameters.set(1, "5");

        // Act

        // Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> changeRatingOfFeedbackCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should change rating when rating is different")
    public void execute_ShouldChangeRating_WhenValidParametersArePassed() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Feedback feedback = taskManagementSystemRepository.createFeedback(team,
                board, "VeryLongTitle", "Description", 5);
        board.addFeedback(feedback);

        parameters.set(0, String.valueOf(feedback.getId()));
        parameters.set(1, "3");

        // Act
        String result = changeRatingOfFeedbackCommand.execute(parameters);

        // Assert
        Assertions.assertEquals(String.format("Rating of feedback with ID %d was changed to %d.",
                feedback.getId(), feedback.getRating(), 3), result);
    }

}
