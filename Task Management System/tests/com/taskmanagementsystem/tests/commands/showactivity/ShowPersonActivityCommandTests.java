package com.taskmanagementsystem.tests.commands.showactivity;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.showactivity.ShowPersonActivityCommand;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.ElementNotFoundException;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.MemberImpl;
import com.taskmanagementsystem.models.contracts.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

/**
 * ShowPersonActivityCommand arguments: {{Name}}
 */
public class ShowPersonActivityCommandTests {
    public static final int EXPECTED_ARGUMENTS_COUNT = 1;
    private Command showPersonActivityCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        showPersonActivityCommand = new ShowPersonActivityCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when arguments count is different than expected")
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> parameters = getList(EXPECTED_ARGUMENTS_COUNT + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> showPersonActivityCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when member is not found")
    public void execute_Should_ThrowException_When_MemberDoesNotExist() {
        List<String> parameters = getList(EXPECTED_ARGUMENTS_COUNT);
        parameters.set(0, "Matthew");

        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class,
                () -> showPersonActivityCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should return correct message when member is found")
    public void execute_Should_ReturnCorrectMessage_When_MemberExists() {
        // Arrange
        List<String> parameters = getList(EXPECTED_ARGUMENTS_COUNT);
        Member member = new MemberImpl("Matthew");
        taskManagementSystemRepository.createMember(member.getName());

        parameters.set(0, "Matthew");

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> showPersonActivityCommand.execute(parameters));
    }
}
