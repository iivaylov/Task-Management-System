package com.taskmanagementsystem.commands.enums;

public enum FeedbackListing {
    RATING,
    TITLE;

    @Override
    public String toString() {
        switch (this) {
            case RATING:
                return "Rating";
            case TITLE:
                return "Title";
            default:
                throw new IllegalArgumentException();
        }
    }
}
