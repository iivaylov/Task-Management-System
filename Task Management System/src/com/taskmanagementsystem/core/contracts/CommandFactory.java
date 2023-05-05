package com.taskmanagementsystem.core.contracts;

import com.taskmanagementsystem.commands.contracts.Command;

public interface CommandFactory {

    Command createCommandFromCommandName(String commandTypeAsString,
                                         TaskManagementSystemRepository taskManagementSystemRepository);
}
