package com.example.to_do.Database;

public class TaskItem {
    private  String name,listName,date,time,priority,description,reminder,priorityDegree;


    private  int done=0;

    public TaskItem(String name, String listName, String date, String time, String priority, String description, String reminder,int done) {
        this.name = name;
        this.listName = listName;
        this.date = date;
        this.time = time;
        this.priority = priority;
        switch (priority)
        {
            case"Urgent":
                priorityDegree="0";
                break;
            case "High":
                priorityDegree="1";
                break;
            case "Normal":
                priorityDegree="2";
                break;
            case "Low":
                priorityDegree="3";
                break;
        }
        this.description = description;
        this.reminder = reminder;
        this.done=done;

    }

    public TaskItem(String name, String listName) {
        this.name = name;
        this.listName = listName;
    }

    public TaskItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public String getPriorityDegree() {
        return priorityDegree;
    }

    public void setPriorityDegree(String priorityDegree) {
        this.priorityDegree = priorityDegree;
    }
}
