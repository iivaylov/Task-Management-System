package com.taskmanagementsystem.utils;

import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.contracts.Task;

import java.util.ArrayList;
import java.util.List;

public class ListingHelpers {


    public static final String DELIMITER = "***************";

    public static String listFeedbackToString(List<Feedback> feedbacks) {
        List<String> result = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            result.add(feedback.toString());
        }
        return String.join(System.lineSeparator() + DELIMITER + System.lineSeparator(), result).trim();
    }

    public static String listStoryToString(List<Story> stories) {
        List<String> result = new ArrayList<>();
        for (Story story : stories) {
            result.add(story.toString());
        }
        return String.join(DELIMITER + System.lineSeparator(), result).trim();
    }


    public static String listBugToString(List<Bug> bugs) {
        List<String> result = new ArrayList<>();
        for (Bug bug : bugs) {
            result.add(bug.toString());
        }
        return String.join(DELIMITER + System.lineSeparator(), result).trim();
    }

    public static String listTasksToString(List<Task> tasks) {
        List<String> result = new ArrayList<>();
        for (Task task : tasks) {
            result.add(task.toString());
        }
        return String.join(System.lineSeparator() + DELIMITER + System.lineSeparator(), result).trim();
    }

}
