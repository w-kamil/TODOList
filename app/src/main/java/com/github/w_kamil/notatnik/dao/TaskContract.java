package com.github.w_kamil.notatnik.dao;

import android.provider.BaseColumns;


public class TaskContract {

    public static final String DB_NAME = "tasksDb";
    public static final int DB_VERSION = 1;


    public class TaksEntry implements BaseColumns{
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_DATE = "data";

    }

    public static String[] COLUMNS_NAMES = {TaksEntry._ID, TaksEntry.COL_TASK_TITLE, TaksEntry.COL_TASK_DATE};

}
