package com.taskmanagementsystem.commands.showactivity;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ShowBoardActivityCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ShowBoardActivityCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        Board board = taskManagementSystemRepository.findBoardInTeam(team, parameters.get(1));
        return showBoardActivity(board);
    }

    private String showBoardActivity(Board board) {
        return board.showBoardActivityHistory();
    }
}
