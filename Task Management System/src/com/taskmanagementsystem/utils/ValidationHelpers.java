package com.taskmanagementsystem.utils;

import com.taskmanagementsystem.exceptions.InvalidUserInputException;

import java.util.List;

public final class ValidationHelpers {

    public static final String NO_SUCH_ENUM = "There is no %s in %ss.";
    private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d, Received: %d";

    public static void validateStringLength(String stringToValidate, int minLength, int maxLength, String errorMessage) {
        if (stringToValidate.length() < minLength || stringToValidate.length() > maxLength) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void validateArgumentsCount(List<String> list, int expectedArgumentsCount) {
        if (list.size() < expectedArgumentsCount || list.size() > expectedArgumentsCount) {
            throw new InvalidUserInputException(String.format(INVALID_NUMBER_OF_ARGUMENTS, expectedArgumentsCount, list.size()));
        }
    }
}
