package com.taskmanagementsystem.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedbackInBoardCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private static final String FEEDBACK_CREATE_MESSAGE = "Feedback with ID %d was created in board %s of team %s.";
    public static final String INVALID_RATING = "Invalid rating!";

    private final TaskManagementSystemRepository taskManagementSystemRepository;


    public CreateFeedbackInBoardCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        Board board = taskManagementSystemRepository.findBoardInTeam(team, parameters.get(1));
        String title = parameters.get(2);
        String description = parameters.get(3);
        int rating = ParsingHelpers.tryParseInteger(parameters.get(4), INVALID_RATING);
        return createFeedbackInBoard(team, board, title, description, rating);
    }

    private String createFeedbackInBoard(Team team, Board board, String title, String description, int rating) {
        Feedback feedback = taskManagementSystemRepository.createFeedback(team, board, title, description, rating);
        taskManagementSystemRepository.findBoardByName(board.getName()).addFeedback(feedback);
        return String.format(FEEDBACK_CREATE_MESSAGE, feedback.getId(), board.getName(), team.getName());
    }
}
