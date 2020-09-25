package com.example.to_do.Database;

public class ListItem {
    private String name;
    private int numberOfTasks;

    public ListItem(String name, int numberOfTasks) {
        this.name = name;
        this.numberOfTasks = numberOfTasks;

    }

    public ListItem(String name_of_list) {
        this.name = name_of_list;
    }  // constructor

    public String getName() {
        return name;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }
}
