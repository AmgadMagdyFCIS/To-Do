package com.example.to_do.Database;

import java.util.Comparator;

public class SortByPriority implements Comparator<TaskItem>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(TaskItem a, TaskItem b)
    {
        return (a.getPriority().charAt(0) - b.getPriority().charAt(0));
    }
}