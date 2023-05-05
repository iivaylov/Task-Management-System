package com.taskmanagementsystem.models;

import com.taskmanagementsystem.models.contracts.History;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryImpl implements History {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
    private final String description;
    private final LocalDateTime date;

    public HistoryImpl(String description) {
        this.description = description;
        this.date = LocalDateTime.now();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String viewInfo() {
        return String.format("[%s] %s", date.format(formatter), description);
    }
}
