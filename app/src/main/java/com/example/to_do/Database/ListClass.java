package com.example.to_do.Database;

public class ListClass {
    public String name_of_list;
    public Integer NumberOfTasks;
    public String Description;

    public ListClass(String name_of_list, Integer numberOfTasks, String description) {
        this.name_of_list = name_of_list;
        NumberOfTasks = numberOfTasks;
        Description = description;
    }

    public ListClass(String name_of_list) {
        this.name_of_list = name_of_list;
    }
}
