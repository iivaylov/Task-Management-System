package com.taskmanagementsystem.commands.showactivity;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ShowPersonActivityCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ShowPersonActivityCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Member member = taskManagementSystemRepository.findMemberByName(parameters.get(0));
        return showPersonActivity(member);
    }

    private String showPersonActivity(Member member) {
        return member.showMemberActivityHistory();
    }
}
