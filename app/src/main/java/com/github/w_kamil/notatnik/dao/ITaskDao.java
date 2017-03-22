package com.github.w_kamil.notatnik.dao;


import java.util.List;

public interface ITaskDao {

    public List<Task> fetchAllTask();
    public boolean addTask (Task task);
    public boolean deleteTask(Task task);


}
