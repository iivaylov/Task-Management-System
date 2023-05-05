package com.taskmanagementsystem.commands.enums;

public enum StoryListing {

    TITLE,
    SIZE;

    @Override
    public String toString() {
        switch (this) {
            case TITLE:
                return "Title";
            case SIZE:
                return "Size";
        }
        throw new IllegalArgumentException();
    }
}
