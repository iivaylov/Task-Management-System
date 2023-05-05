package com.taskmanagementsystem.models;

import com.taskmanagementsystem.models.contracts.Comment;

public class CommentImpl implements Comment {

    private String message;
    private String author;

    public CommentImpl(String message, String author) {
        setMessage(message);
        setAuthor(author);
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format("Message: %s", getMessage()) +
                System.lineSeparator() +
                String.format("Author: %s", getAuthor());
    }
}
