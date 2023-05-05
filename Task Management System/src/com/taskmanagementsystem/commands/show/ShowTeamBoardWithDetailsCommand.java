package com.taskmanagementsystem.commands.show;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamBoardWithDetailsCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ShowTeamBoardWithDetailsCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        Board board = taskManagementSystemRepository.findBoardInTeam(team, parameters.get(1));
        return showTeamBoardWithDetails(team, board);
    }

    private String showTeamBoardWithDetails(Team team, Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("---BOARD OF TEAM { %s }---", team.getName())).append(System.lineSeparator());
        if (team.getBoards().isEmpty()) {
            stringBuilder.append("There is no such board in the team.").append(System.lineSeparator());
            stringBuilder.append("###################################");
            return stringBuilder.toString();
        }
        stringBuilder.append(String.format("%s", board)).append(System.lineSeparator());
        return stringBuilder.toString().trim();
    }
}
