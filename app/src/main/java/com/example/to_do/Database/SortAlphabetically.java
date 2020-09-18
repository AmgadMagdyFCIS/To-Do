package com.example.to_do.Database;

import java.util.Comparator;

public class SortAlphabetically implements Comparator<TaskItem>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(TaskItem a, TaskItem b)
        {
            return (a.getName().charAt(0) - b.getName().charAt(0));
        }
    }