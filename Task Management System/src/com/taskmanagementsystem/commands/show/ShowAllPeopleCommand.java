package com.taskmanagementsystem.commands.show;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ShowAllPeopleCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    private final TaskManagementSystemRepository taskManagementSystemRepository;


    public ShowAllPeopleCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        return showAllPeople();
    }

    private String showAllPeople() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---People---").append(System.lineSeparator());
        if (taskManagementSystemRepository.getMembers().isEmpty()) {
            stringBuilder.append("There are no people in the system.").append(System.lineSeparator());
            stringBuilder.append("###################################");
            return stringBuilder.toString();
        }
        int count = 1;
        for (Member member : taskManagementSystemRepository.getMembers()) {
            stringBuilder.append(String.format("%d. %s%n", count, member.getName()));
            count++;
        }
        return stringBuilder.toString().trim();
    }
}

