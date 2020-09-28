package com.example.to_do.Database;

public class Constants {

    public static final String DATABASE_NAME = "com.example.to_do";



    public static class TaskTable {
        public static final String TABLE_NAME = "Tasks";
        public static final String TASKNAME = "taskname";
        public static final String LISTNAME = "listname";
        public static final String PRIORITY = "priority";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String DONE = "done";
        public static final String REMAINDER = "remainder";

    }

    public static class UserTable {
        public static final String TABLE_NAME = "users";
        public static final String ID = "id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }

//Abdoooooo Whyyyyyy

    public static class ListTable {
        public static final String TABLE_NAME = "Lists";
        public static final String NAME = "name";
        public static final String NUMBER_OF_TASKS = "number_of_tasks";
        public static final String DESCRIPTION = "description";
    }


}
