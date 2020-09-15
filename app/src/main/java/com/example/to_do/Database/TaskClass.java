package com.example.to_do.Database;

public class TaskClass {
    public  String  name_of_task;
    public  String name_of_list ;
    public  String Date ;
    public  String Time ;
    public  Integer Priority ;
    public  String Description ;
    public  String Reminder ;
    public  Integer Done ;

    public TaskClass(String name_of_task, String name_of_list, String date, String time, Integer priority, String description, String reminder, Integer done) {
        this.name_of_task = name_of_task;
        this.name_of_list = name_of_list;
        Date = date;
        Time = time;
        Priority = priority;
        Description = description;
        Reminder = reminder;
        Done = done;
    }
}
