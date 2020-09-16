package com.example.to_do.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ToDoDBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;
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
                " NumberOfTasks Integer ,Description String)");

        sqLiteDatabase.execSQL("create table To_do_Task(name_of_task String primary key," +
                "name_of_list String ,Date String ,Time String ,"
                + "Priority Integer,Description String, Reminder String , Integer Done)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Constants.UserTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists To_do_List");
        sqLiteDatabase.execSQL("drop table if exists To_do_Task");
        onCreate(sqLiteDatabase);
    }




    public void create_Task(TaskClass tc) {
        ContentValues row = new ContentValues();
        Cursor cur = Fetchlist(tc.name_of_list) ;
        row.put("name_of_task", tc.name_of_task);
        row.put("name_of_list", tc.name_of_list);
        increase(tc.name_of_list) ;
        row.put("Date", tc.Date);
        row.put("Time", tc.Time);
        row.put("Priority", tc.Priority);
        row.put("Description", tc.Description);
        row.put("Reminder", tc.Reminder);
        row.put("Done", tc.Done);
        toDoDatabase = getWritableDatabase();
        toDoDatabase.insert("To_do_Task", null, row);
        toDoDatabase.close();
    }
    public void create_list(ListClass lc) {
        ContentValues row = new ContentValues();
        row.put("name_of_list", lc.name_of_list);
        row.put("NumberOfTasks", 0);
        row.put("Description", lc.Description);
        toDoDatabase = getWritableDatabase();
        toDoDatabase.insert("To_do_List", null, row);
        toDoDatabase.close();
    }

    public Cursor fetchAllTasks() {
        toDoDatabase = getReadableDatabase();
        String[] rowDetails = {"name_of_task", "name_of_list", "Date", "Time", "Priority", "Description", "Reminder", "Done"};
        Cursor cursor = toDoDatabase.query("To_do_Task", rowDetails, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        toDoDatabase.close();
        return cursor;
    }

    public Cursor fetchAllLists() {
        toDoDatabase = getReadableDatabase();
        String[] rowDetails = {"name_of_list", "NumberOfTasks", "Description"};
        Cursor cursor = toDoDatabase.query("To_do_List", rowDetails, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        toDoDatabase.close();
        return cursor;
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
        toDoDatabase.close();
    }

    public void DeleteTask(String name) {
        toDoDatabase = getWritableDatabase();
        Cursor cur =Fetchlistusingtask(name) ;
        decrease(cur.getString(1)) ;
        toDoDatabase.delete("To_do_Task", "name_of_task='" + name + "'", null);
        toDoDatabase.close();
    }

    public void UpdateList(String NEWname_of_list, String OldName, String NewDescription) {
        toDoDatabase = getReadableDatabase();
        Cursor cur =Fetchlist(OldName) ;
        ContentValues row = new ContentValues();
        row.put("name_of_list", NEWname_of_list);
        row.put("NumberOfTasks", cur.getInt(1));
        row.put("Description", NewDescription);
        toDoDatabase.update("To_do_List", row, "name_of_list like ?", new String[]{OldName});
        toDoDatabase.close();
    }

    public void DeleteList(String name) {
        toDoDatabase = getWritableDatabase();
        toDoDatabase.delete("To_do_List", "name_of_list='" + name + "'", null);
        toDoDatabase.close();
    }

    public ArrayList<TaskClass> ReturnTasksOfSpecificeList(String NameList, Integer done) {
        ArrayList<TaskClass> List = new ArrayList<>();
        toDoDatabase = getReadableDatabase();

        String[] arg = {NameList};
        // Cursor curs = tododatabase.rawQuery("Select name_of_task from To_do_Task where NameList like ?", arg);
        Cursor curs = fetchAllTasks();
        while (!curs.isAfterLast()) {
            if (curs.getString(1).equalsIgnoreCase(NameList) && curs.getInt(7) == done) {
                List.add(new TaskClass(curs.getString(0),curs.getString(1),curs.getString(2),curs.getString(3),curs.getInt(4),curs.getString(5),curs.getString(6) ,curs.getInt(7)));
            }
            curs.moveToNext();
        }
        curs.close();
        toDoDatabase.close();
        return List;
    }


    public Cursor Fetchlist (String name) {
        // Cursor cur = fetchAllLists();
        //cur.getInt()
        toDoDatabase =getReadableDatabase();
        String [] arg={name} ;
        Cursor cur = toDoDatabase.rawQuery("Select * from To_do_List where name like ?",arg) ;
        toDoDatabase.close();
        return  cur ;
    }


    public Cursor FetchSpecificeTask (String tname) {
        toDoDatabase =getReadableDatabase();
        String [] arg={tname} ;
        Cursor cur = toDoDatabase.rawQuery("Select * from To_do_Task where tname like ?",arg) ;
        toDoDatabase.close();
        return  cur ;
    }


    public void increase(String OldName){
        Cursor cur =Fetchlist(OldName) ;
        toDoDatabase = getReadableDatabase();
        ContentValues row = new ContentValues();
        row.put("name_of_list", OldName);
        row.put("NumberOfTasks", (cur.getInt(1)+1)) ;
        row.put("Description", cur.getString(2));
        toDoDatabase.update("To_do_List", row, "name_of_list like ?", new String[]{OldName});
        toDoDatabase.close();
    }

    public void decrease(String OldName){
        Cursor cur =Fetchlist(OldName) ;
        toDoDatabase = getReadableDatabase();
        ContentValues row = new ContentValues();
        row.put("name_of_list", OldName);
        row.put("NumberOfTasks", (cur.getInt(1)-1)) ;
        row.put("Description", cur.getString(2));
        toDoDatabase.update("To_do_List", row, "name_of_list like ?", new String[]{OldName});
        toDoDatabase.close();
    }

    public Cursor Fetchlistusingtask (String tname) {
        // Cursor cur = fetchAllLists();
        //cur.getInt()
        toDoDatabase =getReadableDatabase();
        String [] arg={tname} ;
        Cursor cur = toDoDatabase.rawQuery("Select * from To_do_Task where name like ?",arg) ;
        toDoDatabase.close();
        return  cur ;
    }










    public void SignUp(String firstName, String lastName,String email, String password) {

        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Constants.UserTable.FIRST_NAME, firstName);
        values.put(Constants.UserTable.LAST_NAME, lastName);
        values.put(Constants.UserTable.EMAIL, email);
        values.put(Constants.UserTable.PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        db.insert(Constants.UserTable.TABLE_NAME, null, values);
        db.close();
    }

    public boolean ValidateUserData(String email, String password) {
        boolean isFound = false;
        String[] a = {email};
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constants.UserTable.TABLE_NAME + " WHERE " + Constants.UserTable.EMAIL + "= ?";
        Cursor cursor = database.rawQuery(query, a);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(3).equalsIgnoreCase(email)) {
                    if (cursor.getString(4).equalsIgnoreCase(password)) {
                        isFound = true;
                    }
                } else
                    isFound = false;
            }
        }
        cursor.close();
        return isFound;
    }

    public boolean isEmailFound(String email) {
        boolean isFound = false;
        SQLiteDatabase database = this.getReadableDatabase();
        String[] a = {email};
        String query = "SELECT * FROM " + Constants.UserTable.TABLE_NAME + " WHERE " + Constants.UserTable.EMAIL + " = ?";
        Cursor cursor = database.rawQuery(query, a);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(3).equalsIgnoreCase(email)) {
                    isFound = true;
                    break;
                }
            }
        }
        database.close();
        cursor.close();
        return isFound;
    }

    public boolean isPasswordFound(String password) {
        boolean isFound = false;
        SQLiteDatabase database = this.getReadableDatabase();
        String[] a = {password};
        String query = "SELECT * FROM " + Constants.UserTable.TABLE_NAME + " WHERE " + Constants.UserTable.PASSWORD + " = ?";
        Cursor cursor = database.rawQuery(query, a);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(4).equalsIgnoreCase(password)) {
                    isFound = true;
                    break;
                }
            }
        }
        database.close();
        cursor.close();
        return isFound;
    }

    public Cursor GetEmail(String email) {
        boolean isFound = false;
        String[] a = {email};
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constants.UserTable.TABLE_NAME + " WHERE " + Constants.UserTable.EMAIL + "= ?";
        Cursor cursor = database.rawQuery(query, a);
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
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(Constants.UserTable.TABLE_NAME, row, Constants.UserTable.ID + "='" + cursor.getInt(0) + "'", null);
        sqLiteDatabase.close();
    }


}
