package com.taskmanagementsystem.utils;

import com.taskmanagementsystem.exceptions.InvalidUserInputException;

import java.util.List;

public class ValidationHelpers {

    // TODO: 17.12.2022 г. 1. Add more validations
    private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d, Received: %d";

    public static final String NO_SUCH_ENUM = "There is no %s in %ss.";


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
/*
    public static void validateStringMinLength(String value, int minLength) {
        if (value == null || value.length() < minLength) {
            throw new InvalidUserInputException();
        }
    }*/

//    public static <E extends Enum<E>> E tryParseEnum(String valueToParse, Class<E> type) {
//        try {
//            return Enum.valueOf(type, valueToParse.replace(" ", "_").toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException(String.format(NO_SUCH_ENUM, valueToParse, type.getSimpleName()));
//        }
//    }

}
