package com.taskmanagementsystem.commands.show;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ShowAllTeamsCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ShowAllTeamsCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        return showAllTeams();
    }

    private String showAllTeams() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---TEAMS---").append(System.lineSeparator());
        if (taskManagementSystemRepository.getTeams().isEmpty()) {
            stringBuilder.append("There are no teams in the system.").append(System.lineSeparator());
            stringBuilder.append("###################################");
            return stringBuilder.toString();
        }
        int count = 1;
        for (Team team : taskManagementSystemRepository.getTeams()) {
            stringBuilder.append(String.format("%d. %s%n", count, team.getName()));
            count++;
        }
        return stringBuilder.toString().trim();
    }
}
