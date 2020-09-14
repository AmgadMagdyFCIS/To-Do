package com.example.to_do.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;


    public ToDoDBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Constants.UserTable.TABLE_NAME
                + " (" + Constants.UserTable.ID + " integer primary key, "
                + Constants.UserTable.FIRST_NAME + " text not null, " + Constants.UserTable.LAST_NAME + " text not null, "
                + Constants.UserTable.EMAIL + " text not null, " + Constants.UserTable.PASSWORD + " text not null " + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Constants.UserTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.TaskTable.TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.ListTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
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
