package com.github.w_kamil.notatnik.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class Database {

    public static TaskDao taskDao;
    DbHelper dbHelper;

    public Database(Context context) {
        this.dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        taskDao = new TaskDao(db);
    }

    public class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context) {
            super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + TaskContract.TaksEntry.TABLE + " (" + TaskContract.TaksEntry._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaksEntry.COL_TASK_TITLE + " TEXT, "
                    + TaskContract.TaksEntry.COL_TASK_DATE + " TEXT);";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaksEntry.TABLE);
            onCreate(db);
        }
    }

 }



