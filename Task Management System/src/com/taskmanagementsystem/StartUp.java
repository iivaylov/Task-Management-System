package com.taskmanagementsystem;

import com.taskmanagementsystem.core.TaskManagementSystemEngineImpl;

public class StartUp {
    public static void main(String[] args) {

        TaskManagementSystemEngineImpl engine = new TaskManagementSystemEngineImpl();
        engine.start();


    }
}
