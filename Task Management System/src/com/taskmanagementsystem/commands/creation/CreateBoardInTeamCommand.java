package com.taskmanagementsystem.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class CreateBoardInTeamCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private static final String BOARD_CREATE_MESSAGE = "Board with name %s was created successfully in team %s.";
    public static final String BOARD_EXISTS_MESSAGE = "Board with that name already exists in this team!";

    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public CreateBoardInTeamCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        String boardName = parameters.get(1);
        return createBoardInTeam(team, boardName);
    }

    private String createBoardInTeam(Team teamName, String boardName) {
        if (teamName.getBoards().stream().anyMatch(b -> b.getName().equalsIgnoreCase(boardName))) {
            throw new InvalidUserInputException(BOARD_EXISTS_MESSAGE);
        }
        Board board = taskManagementSystemRepository.createBoard(teamName, boardName);
        taskManagementSystemRepository.findTeamByName(teamName.getName()).addBoard(board);
        return String.format(BOARD_CREATE_MESSAGE, board.getName(), teamName.getName());
    }
}
