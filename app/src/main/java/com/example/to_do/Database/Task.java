package com.example.to_do.Database;

public class Task {
    private  String  name_of_task;
    private  String name_of_list ;
    private  String Date ;
    private  String Time ;
    private  String Priority ;
    private  String Description ;
    private  String Reminder ;
    private  Integer Done ;

    public Task(String name_of_task, String name_of_list, String date, String time, String priority, String description, String reminder, Integer done) {
        this.name_of_task = name_of_task;
        this.name_of_list = name_of_list;
        Date = date;
        Time = time;
        Priority = priority;
        Description = description;
        Reminder = reminder;
        Done = done;
    }

    public Task(String name_of_task, String name_of_list) {
        this.name_of_task = name_of_task;
        this.name_of_list = name_of_list;
    }

    public Task() {
    }

    public String getName_of_task() {
        return name_of_task;
    }

    public void setName_of_task(String name_of_task) {
        this.name_of_task = name_of_task;
    }

    public String getName_of_list() {
        return name_of_list;
    }

    public void setName_of_list(String name_of_list) {
        this.name_of_list = name_of_list;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getReminder() {
        return Reminder;
    }

    public void setReminder(String reminder) {
        Reminder = reminder;
    }

    public Integer getDone() {
        return Done;
    }

    public void setDone(Integer done) {
        Done = done;
    }
}
