package com.taskmanagementsystem.commands.enums;

public enum Order {
    ASCENDING,
    DESCENDING;

    @Override
    public String toString() {
        switch (this) {
            case ASCENDING:
                return "Ascending";
            case DESCENDING:
                return "Descending";
            default:
                throw new IllegalArgumentException();
        }
    }
}
