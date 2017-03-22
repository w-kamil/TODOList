package com.github.w_kamil.notatnik;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.github.w_kamil.notatnik.dao.Task;

import java.util.List;


public class TasksAdapter extends ArrayAdapter<Task> {


    public TasksAdapter(@NonNull Context context, @NonNull List<Task> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_item,parent,false);
        }
        TextView taskId = (TextView) convertView.findViewById(R.id.task_id);
        TextView taskTitle = (TextView) convertView.findViewById(R.id.task_title);
        TextView taskDate = (TextView) convertView.findViewById(R.id.date_text);
        taskId.setText(String.valueOf(task.id));
        taskTitle.setText(task.title);
        taskDate.setText(task.data);
        return convertView;
    }
}
