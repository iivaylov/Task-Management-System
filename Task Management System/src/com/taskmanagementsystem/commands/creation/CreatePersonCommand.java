package com.taskmanagementsystem.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class CreatePersonCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String PERSON_CREATE_MESSAGE = "Person with name %s was created successfully.";

    private final TaskManagementSystemRepository taskManagementSystemRepository;

    private String name;


    public CreatePersonCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        Member createMember = taskManagementSystemRepository.createMember(name);
        return String.format(PERSON_CREATE_MESSAGE, createMember.getName());
    }

    private void parseParameters(List<String> parameters) {
        name = parameters.get(0);
    }
}
