package com.example.to_do.Recyclers;

public class ListItem {
    private String name, description;
    private int numberOfTasks;

    public ListItem(String name, int numberOfTasks, String description) {
        this.name = name;
        this.numberOfTasks = numberOfTasks;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public String getDescription() {
        return description;
    }
}
