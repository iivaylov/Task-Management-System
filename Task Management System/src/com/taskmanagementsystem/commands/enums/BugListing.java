package com.taskmanagementsystem.commands.enums;

public enum BugListing {
    TITLE,
    PRIORITY,
    SEVERITY;

    @Override
    public String toString() {
        switch (this) {
            case TITLE:
                return "Title";
            case PRIORITY:
                return "Priority";
            case SEVERITY:
                return "Severity";
        }
        throw new IllegalArgumentException();
    }
}
