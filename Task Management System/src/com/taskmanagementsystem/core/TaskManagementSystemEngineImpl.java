package com.taskmanagementsystem.core;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.CommandFactory;
import com.taskmanagementsystem.core.contracts.Engine;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManagementSystemEngineImpl implements Engine {

    private static final String TERMINATION_COMMAND = "Exit";
    private static final String EMPTY_COMMAND = "Command cannot be empty.";

    private final CommandFactory commandFactory;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public TaskManagementSystemEngineImpl() {
        commandFactory = new CommandFactoryImpl();
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String inputLine = scanner.nextLine();
                if (inputLine.isBlank()) {
                    System.out.println(EMPTY_COMMAND);
                    continue;
                }
                if (inputLine.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(inputLine);
            } catch (Exception ex) {
                if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                    System.out.println(ex.getMessage());
                } else {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private void processCommand(String inputLine) {
        String commandName = extractCommandName(inputLine);
        Command command = commandFactory.createCommandFromCommandName(commandName, taskManagementSystemRepository);
        List<String> parameters = extractCommandParameters(inputLine);
        String commandResult = command.execute(parameters);
        System.out.println(commandResult);
    }

    private String extractCommandName(String inputLine) {
        return inputLine.split(" ")[0];
    }

    private List<String> extractCommandParameters(String inputLine) {
        String[] commandParts = inputLine.split(" ");
        ArrayList<String> parameters = new ArrayList<>();
        parameters.addAll(Arrays.asList(commandParts).subList(1, commandParts.length));
        return parameters;
    }

}
