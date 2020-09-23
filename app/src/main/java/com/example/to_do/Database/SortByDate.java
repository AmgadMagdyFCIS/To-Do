package com.example.to_do.Database;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Comparator;

public class SortByDate implements Comparator<TaskItem> {

    // Used for sorting in ascending order of
    // roll number

    @Override
    public int compare(TaskItem taskItem, TaskItem t1) {
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(taskItem.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(t1.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (date1.compareTo(date2));

    }


}
