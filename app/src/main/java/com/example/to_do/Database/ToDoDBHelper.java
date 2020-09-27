package com.example.to_do.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.to_do.UI.Login;

import java.util.ArrayList;
import java.util.List;

public class ToDoDBHelper extends SQLiteOpenHelper {

   // private SQLiteDatabase sqLiteDatabase;
    private SQLiteDatabase toDoDatabase;


    public ToDoDBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Constants.UserTable.TABLE_NAME
                + " (" + Constants.UserTable.ID + " integer primary key, "
                + Constants.UserTable.FIRST_NAME + " text not null, " + Constants.UserTable.LAST_NAME + " text not null, "
                + Constants.UserTable.EMAIL + " text not null, " + Constants.UserTable.PASSWORD + " text not null " + ")");

        sqLiteDatabase.execSQL("create table To_do_List (name_of_list String primary Key," +
                " NumberOfTasks Integer, email String )");

        sqLiteDatabase.execSQL("create table To_do_Task(name_of_task String primary key," +
                "name_of_list String ,Date String ,Time String ,"
                + "Priority String,Description String, Reminder String , Done Integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Constants.UserTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists To_do_List");
        sqLiteDatabase.execSQL("drop table if exists To_do_Task");
        onCreate(sqLiteDatabase);
    }


    public void create_Task(TaskItem tc) {
        ContentValues row = new ContentValues();
        row.put("name_of_task", tc.getName());
        row.put("name_of_list", tc.getListName());
       increase(tc.getListName()) ;
        row.put("Date", tc.getDate());
        row.put("Time", tc.getTime());
        row.put("Priority", tc.getPriority());
        row.put("Description", tc.getDescription());
        row.put("Reminder", tc.getReminder());
        row.put("Done", 0);
        toDoDatabase = getWritableDatabase();
        toDoDatabase.insert("To_do_Task", null, row);
    }

    public void create_list(ListItem lc) {
        ContentValues row = new ContentValues();
        row.put("name_of_list", lc.getName());
        row.put("NumberOfTasks", 0);
        row.put("email", Login.mainEmail);
        toDoDatabase = getWritableDatabase();
        toDoDatabase.insert("To_do_List", null, row);

    }

    public Cursor fetchAllTasks() {
        toDoDatabase = getReadableDatabase();
        String[] rowDetails = {"name_of_task", "name_of_list", "Date", "Time", "Priority", "Description", "Reminder", "Done"};
        Cursor cursor = toDoDatabase.query("To_do_Task", rowDetails, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchAllLists() {
        toDoDatabase = getReadableDatabase();
        String[] args = {Login.mainEmail};
        Cursor cursor = toDoDatabase.rawQuery("select * from To_do_List where email = ?",args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List<String> getAllLists() {
        List<String> list = new ArrayList<String>();
        String[] args = {Login.mainEmail};
        String selectQuery = "select * from To_do_List where email = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, args);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public void UpdateTask(String Newname_of_task, String OldName, String Newname_of_list, String NewDate, String NewTime, Integer NewPriority, String NewDescription, String NewReminder, Integer NewDone) {
        toDoDatabase = getReadableDatabase();
        ContentValues row = new ContentValues();
        row.put("name_of_task", Newname_of_task);
        row.put("name_of_list", Newname_of_list);
        row.put("Date", NewDate);
        row.put("Time", NewTime);
        row.put("Priority", NewPriority);
        row.put("Description", NewDescription);
        row.put("Reminder", NewReminder);
        row.put("Done", NewDone);
        toDoDatabase.update("To_do_Task", row, "name_of_task like ?", new String[]{OldName});

    }

    public void FinishTask(String taskName) {
        Cursor cur = fetchTask(taskName);

        toDoDatabase = getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("name_of_task", cur.getString(0));
        row.put("name_of_list", cur.getString(1));
        row.put("Date", cur.getString(2));
        row.put("Time", cur.getString(3));
        row.put("Priority", cur.getString(4));
        row.put("Description", cur.getString(5));
        row.put("Reminder", cur.getString(6));

        if (cur.getInt(7) == 0)
            row.put("Done", 1);
        else
            row.put("Done", 0);

        toDoDatabase.update("To_do_Task", row, "name_of_task like ?", new String[]{cur.getString(0)});

    }
    public void editTask(String name,TaskItem taskItem) {
        Cursor cur = fetchTask(name);

        toDoDatabase = getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("name_of_task", taskItem.getName());
        row.put("name_of_list", taskItem.getListName());
        row.put("Date", taskItem.getDate());
        row.put("Time", taskItem.getTime());
        row.put("Priority", taskItem.getPriority());
        row.put("Description", taskItem.getDescription());
        row.put("Reminder", taskItem.getReminder());
        row.put("Done", taskItem.getDone());


        toDoDatabase.update("To_do_Task", row, "name_of_task like ?", new String[]{cur.getString(0)});

    }


    public void deleteTask(String name) {
        toDoDatabase = getWritableDatabase();
        Cursor cur = fetchTask(name);
        decrease(cur.getString(1));
        toDoDatabase.delete("To_do_Task", "name_of_task='" + name + "'", null);

    }

    public void UpdateList(String NEWname_of_list, String OldName) {
        toDoDatabase = getWritableDatabase();
        Cursor cur = Fetchlist(OldName);
        ContentValues row = new ContentValues();
        row.put("name_of_list", NEWname_of_list);
        row.put("NumberOfTasks", cur.getInt(1));
        row.put("email",cur.getString(2));
        toDoDatabase.update("To_do_List", row, "name_of_list like ?", new String[]{OldName});

    }

    public void DeleteList(String name) {
        ClearList(name);
        toDoDatabase = getWritableDatabase();
        toDoDatabase.delete("To_do_List", "name_of_list='" + name + "'", null);
    }

    public ArrayList<TaskItem> returnTasksOfSpecificList(String NameList, int done) {
        ArrayList<TaskItem> List = new ArrayList<>();
        toDoDatabase = getReadableDatabase();

        String[] arg = {NameList};
        Cursor curs = fetchAllTasks();
        while (!curs.isAfterLast()) {
            if (curs.getString(1).equalsIgnoreCase(NameList) && curs.getInt(7) == done) {
                List.add(new TaskItem(curs.getString(0), curs.getString(1), curs.getString(2), curs.getString(3), curs.getString(4), curs.getString(5), curs.getString(6),curs.getInt(7)));
            }
            curs.moveToNext();
        }
        curs.close();

        return List;
    }


    public Cursor Fetchlist(String Listname) {
        toDoDatabase = getReadableDatabase();
        String[] arg = {Listname};
        Cursor cur = toDoDatabase.rawQuery("Select * from To_do_List where name_of_list like ?", arg);
        if (cur != null) {
            cur.moveToFirst();
        }
        return cur;
    }

    public void increase(String ListName) {
        Cursor cur = Fetchlist(ListName);
        toDoDatabase = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name_of_list", cur.getString(0));
        int n=cur.getInt(1) ;
        row.put("NumberOfTasks", (n+1));
        row.put("email",cur.getString(2));
        toDoDatabase.update("To_do_List", row, "name_of_list like ?", new String[]{cur.getString(0)});

    }

    public void decrease(String ListName) {
        Cursor cur = Fetchlist(ListName);
        toDoDatabase = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name_of_list", cur.getString(0));
        row.put("NumberOfTasks", (cur.getInt(1) - 1));
        row.put("email",cur.getString(2));
        toDoDatabase.update("To_do_List", row, "name_of_list like ?", new String[]{cur.getString(0)});

    }

    public Cursor fetchTask(String tName) {
        toDoDatabase = getReadableDatabase();
        String[] arg = {tName};
        Cursor cur = toDoDatabase.rawQuery("Select * from To_do_Task where name_of_task Like '%" + tName + "%'", null);
        if (cur != null) {
            cur.moveToFirst();
        }
        return cur;
    }


    public void ClearList(String listName) {
        toDoDatabase = getWritableDatabase();
        toDoDatabase.delete("To_do_Task", "name_of_list='" + listName + "'", null);

        Cursor cur = Fetchlist(listName);
        toDoDatabase = getReadableDatabase();
        ContentValues row = new ContentValues();
        row.put("name_of_list", cur.getString(0));
        row.put("NumberOfTasks", 0);
        row.put("email",cur.getString(2));
        toDoDatabase.update("To_do_List", row, "name_of_list like ?", new String[]{cur.getString(0)});
    }

    public void SignUp(String firstName, String lastName, String email, String password) {

        toDoDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.UserTable.FIRST_NAME, firstName);
        values.put(Constants.UserTable.LAST_NAME, lastName);
        values.put(Constants.UserTable.EMAIL, email);
        values.put(Constants.UserTable.PASSWORD, password);
        toDoDatabase.insert(Constants.UserTable.TABLE_NAME, null, values);
        toDoDatabase.close();
    }

    public boolean isEmailFound(String email) {
        boolean isFound = false;
        toDoDatabase = this.getReadableDatabase();
        String[] a = {email};
        String query = "SELECT * FROM " + Constants.UserTable.TABLE_NAME + " WHERE " + Constants.UserTable.EMAIL + " = ?";
        Cursor cursor = toDoDatabase.rawQuery(query, a);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(3).equalsIgnoreCase(email)) {
                    isFound = true;
                    break;
                }
            }
        }
        toDoDatabase.close();
        cursor.close();
        return isFound;
    }

    public boolean isPasswordFound(String password) {
        boolean isFound = false;
        toDoDatabase = this.getReadableDatabase();
        String[] a = {password};
        String query = "SELECT * FROM " + Constants.UserTable.TABLE_NAME + " WHERE " + Constants.UserTable.PASSWORD + " = ?";
        Cursor cursor = toDoDatabase.rawQuery(query, a);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(4).equalsIgnoreCase(password)) {
                    isFound = true;
                    break;
                }
            }
        }
        toDoDatabase.close();
        cursor.close();
        return isFound;
    }

    public Cursor GetEmail(String email) {
        boolean isFound = false;
        String[] a = {email};
        toDoDatabase= this.getReadableDatabase();
        String query = "SELECT * FROM " + Constants.UserTable.TABLE_NAME + " WHERE " + Constants.UserTable.EMAIL + "= ?";
        Cursor cursor = toDoDatabase.rawQuery(query, a);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(3).equalsIgnoreCase(email)) {
                    isFound = true;
                    break;
                }
            }
        }
        return cursor;
    }


    public void RecoverPassword(String email, String password) {

        Cursor cursor = GetEmail(email);

        ContentValues row = new ContentValues();
        row.put(Constants.UserTable.FIRST_NAME, cursor.getString(1));
        row.put(Constants.UserTable.LAST_NAME, cursor.getString(2));
        row.put(Constants.UserTable.EMAIL, cursor.getString(3));
        row.put(Constants.UserTable.PASSWORD, password);
        toDoDatabase = getWritableDatabase();
        toDoDatabase.update(Constants.UserTable.TABLE_NAME, row, Constants.UserTable.ID + "='" + cursor.getInt(0) + "'", null);
        toDoDatabase.close();
    }


}
