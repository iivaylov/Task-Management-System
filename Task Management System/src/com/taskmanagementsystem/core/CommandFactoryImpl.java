package com.taskmanagementsystem.core;

import com.taskmanagementsystem.commands.add.AddCommentToTaskCommand;
import com.taskmanagementsystem.commands.add.AddPersonToTeamCommand;
import com.taskmanagementsystem.commands.change.ChangeAssignmentToPersonCommand;
import com.taskmanagementsystem.commands.change.bug.ChangePriorityOfBugCommand;
import com.taskmanagementsystem.commands.change.bug.ChangeSeverityOfBugCommand;
import com.taskmanagementsystem.commands.change.bug.ChangeStatusOfBugCommand;
import com.taskmanagementsystem.commands.change.feedback.ChangeRatingOfFeedbackCommand;
import com.taskmanagementsystem.commands.change.feedback.ChangeStatusOfFeedbackCommand;
import com.taskmanagementsystem.commands.change.story.ChangePriorityOfStoryCommand;
import com.taskmanagementsystem.commands.change.story.ChangeSizeOfStoryCommand;
import com.taskmanagementsystem.commands.change.story.ChangeStatusOfStoryCommand;
import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.creation.*;
import com.taskmanagementsystem.commands.enums.CommandType;
import com.taskmanagementsystem.commands.list.ListAllTasksCommand;
import com.taskmanagementsystem.commands.list.bugs.ListBugsFilterAndSortCommand;
import com.taskmanagementsystem.commands.list.bugs.ListBugsOnlyCommand;
import com.taskmanagementsystem.commands.list.feedbacks.ListFeedbacksFilterAndSortCommand;
import com.taskmanagementsystem.commands.list.feedbacks.ListFeedbacksOnlyCommand;
import com.taskmanagementsystem.commands.list.stories.ListStoriesFilterAndSortCommand;
import com.taskmanagementsystem.commands.list.stories.ListStoryOnlyCommand;
import com.taskmanagementsystem.commands.show.*;
import com.taskmanagementsystem.commands.showactivity.ShowBoardActivityCommand;
import com.taskmanagementsystem.commands.showactivity.ShowPersonActivityCommand;
import com.taskmanagementsystem.commands.showactivity.ShowTeamActivityCommand;
import com.taskmanagementsystem.core.contracts.CommandFactory;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.utils.ParsingHelpers;


public class CommandFactoryImpl implements CommandFactory {

    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    @Override
    public Command createCommandFromCommandName(String commandName,
                                                TaskManagementSystemRepository taskManagementSystemRepository) {
        CommandType commandType = ParsingHelpers.tryParseEnum(commandName, CommandType.class,
                String.format(INVALID_COMMAND, commandName));


        switch (commandType) {
            case CREATEPERSON:
                return new CreatePersonCommand(taskManagementSystemRepository);
            case SHOWALLPEOPLE:
                return new ShowAllPeopleCommand(taskManagementSystemRepository);
            case SHOWPERSONACTIVITY:
                return new ShowPersonActivityCommand(taskManagementSystemRepository);
            case CREATETEAM:
                return new CreateTeamCommand(taskManagementSystemRepository);
            case SHOWALLTEAMS:
                return new ShowAllTeamsCommand(taskManagementSystemRepository);
            case SHOWTEAMACTIVITY:
                return new ShowTeamActivityCommand(taskManagementSystemRepository);
            case ADDPERSONTOTEAM:
                return new AddPersonToTeamCommand(taskManagementSystemRepository);
            case SHOWALLTEAMMEMBERS:
                return new ShowAllTeamMembersCommand(taskManagementSystemRepository);
            case CREATEBOARDINTEAM:
                return new CreateBoardInTeamCommand(taskManagementSystemRepository);
            case SHOWALLTEAMBOARDS:
                return new ShowAllTeamBoardsCommand(taskManagementSystemRepository);
            case SHOWTEAMBOARDWITHDETAILS:
                return new ShowTeamBoardWithDetailsCommand(taskManagementSystemRepository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivityCommand(taskManagementSystemRepository);
            case CREATEBUGINBOARD:
                return new CreateBugInBoardCommand(taskManagementSystemRepository);
            case CREATESTORYINBOARD:
                return new CreateStoryInBoardCommand(taskManagementSystemRepository);
            case CREATEFEEDBACKINBOARD:
                return new CreateFeedbackInBoardCommand(taskManagementSystemRepository);
            case CHANGEPRIORITYOFBUG:
                return new ChangePriorityOfBugCommand(taskManagementSystemRepository);
            case CHANGESEVERITYOFBUG:
                return new ChangeSeverityOfBugCommand(taskManagementSystemRepository);
            case CHANGESTATUSOFBUG:
                return new ChangeStatusOfBugCommand(taskManagementSystemRepository);
            case CHANGEPRIORITYOFSTORY:
                return new ChangePriorityOfStoryCommand(taskManagementSystemRepository);
            case CHANGESIZEOFSTORY:
                return new ChangeSizeOfStoryCommand(taskManagementSystemRepository);
            case CHANGESTATUSOFSTORY:
                return new ChangeStatusOfStoryCommand(taskManagementSystemRepository);
            case CHANGERATINGOFFEEDBACK:
                return new ChangeRatingOfFeedbackCommand(taskManagementSystemRepository);
            case CHANGESTATUSOFFEEDBACK:
                return new ChangeStatusOfFeedbackCommand(taskManagementSystemRepository);
            case CHANGEASSIGNMENTTOPERSON:
                return new ChangeAssignmentToPersonCommand(taskManagementSystemRepository);
            case ADDCOMMENTTOTASK:
                return new AddCommentToTaskCommand(taskManagementSystemRepository);
            case LISTALLTASKS:
                return new ListAllTasksCommand(taskManagementSystemRepository);
            case LISTBUGSONLY:
                return new ListBugsOnlyCommand(taskManagementSystemRepository);
            case LISTSTORIESONLY:
                return new ListStoryOnlyCommand(taskManagementSystemRepository);
            case LISTFEEDBACKSONLY:
                return new ListFeedbacksOnlyCommand(taskManagementSystemRepository);
            case LISTBUGSFILTERANDSORT:
                return new ListBugsFilterAndSortCommand(taskManagementSystemRepository);
            case LISTFEEDBACKSFILTERANDSORT:
                return new ListFeedbacksFilterAndSortCommand(taskManagementSystemRepository);
            case LISTSTORIESFILTERANDSORT:
                return new ListStoriesFilterAndSortCommand(taskManagementSystemRepository);
        }
        throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));
    }
}