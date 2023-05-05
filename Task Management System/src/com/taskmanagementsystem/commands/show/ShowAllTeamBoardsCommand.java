package com.taskmanagementsystem.commands.show;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ShowAllTeamBoardsCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final TaskManagementSystemRepository taskManagementSystemRepository;


    public ShowAllTeamBoardsCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        return showAllTeamBoards(team);
    }

    private String showAllTeamBoards(Team team) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("---BOARDS OF TEAM { %s }---", team.getName()))
                .append(System.lineSeparator());
        if (team.getBoards().isEmpty()) {
            stringBuilder.append("There are no boards in the team.").append(System.lineSeparator());
            stringBuilder.append("###################################");
            return stringBuilder.toString();
        }
        int count = 1;
        for (Board board : team.getBoards()) {
            stringBuilder.append(String.format("%d. %s%n", count, board.getName()));
            count++;
        }
        return stringBuilder.toString().trim();
    }
}