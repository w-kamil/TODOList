package com.github.w_kamil.notatnik.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TaskDao extends DbContentProvider implements ITaskDao {


    public TaskDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public List<Task> fetchAllTask() {
        Cursor cursor = query(TaskContract.TaksEntry.TABLE, TaskContract.COLUMNS_NAMES);
        List<Task> taskList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaksEntry._ID);
            int id = cursor.getInt(idx);
            int indexTitle = cursor.getColumnIndex(TaskContract.TaksEntry.COL_TASK_TITLE);
            int indexData = cursor.getColumnIndex(TaskContract.TaksEntry.COL_TASK_DATE);
            String title = cursor.getString(indexTitle);
            String data = cursor.getString(indexData);
            Task newTask = new Task();
            newTask.id = id;
            newTask.data = data;
            newTask.title = title;
            taskList.add(newTask);
        }
        return taskList;
    }

    @Override
    public boolean addTask(Task task) {

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaksEntry.COL_TASK_TITLE, task.title);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        values.put(TaskContract.TaksEntry.COL_TASK_DATE, currentDate);

        return insert(TaskContract.TaksEntry.TABLE, values);

    }

    @Override
    public boolean deleteTask(Task task) {
        String selectionString = TaskContract.TaksEntry.COL_TASK_TITLE + " = ? AND " + TaskContract.TaksEntry._ID + " = ?";
        String [] selectionArgs = {task.title, String.valueOf(task.id)};

        return delete(TaskContract.TaksEntry.TABLE, selectionString, selectionArgs);

    }
}
