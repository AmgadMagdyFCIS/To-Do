package com.example.to_do.Database;

public class ListItem {
    private String name, description;
    private int numberOfTasks;

    public ListItem(String name, int numberOfTasks, String description) {
        this.name = name;
        this.numberOfTasks = numberOfTasks;
        this.description = description;
    }

    public ListItem(String name_of_list) {
        this.name = name_of_list;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }
}
