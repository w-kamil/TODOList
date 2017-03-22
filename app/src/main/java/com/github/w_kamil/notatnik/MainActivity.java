package com.github.w_kamil.notatnik;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.w_kamil.notatnik.dao.Database;
import com.github.w_kamil.notatnik.dao.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Tag";
    Database dbAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAccess = new Database(this);
        updateUI();
    }

    private void updateUI() {
        new LoadTasks().execute();
    }

    public void deleteTask(View view) {

        View parent = (View) view.getParent().getParent();
        TextView taskView = (TextView) parent.findViewById(R.id.task_title);
        TextView idView = (TextView) parent.findViewById(R.id.task_id);
        String[] task = {String.valueOf(taskView.getText()), String.valueOf(idView.getText())};
        new DeleteTasks().execute(task);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_text:

                final EditText teskEditText = new EditText(this);

                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Add new task").setMessage("Jakie zadanie dodajesz?").setView(teskEditText)
                        .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String taskText = String.valueOf(teskEditText.getText());
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                                String currentDate = dateFormat.format(Calendar.getInstance().getTime());
                                Task newTask = new Task();
                                newTask.title = taskText;
                                newTask.data = currentDate;
                                new InsertTasks().execute(newTask);
                                updateUI();
                            }
                        })
                        .setNegativeButton("Anuluj", null).create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    abstract private class BaseTask<T> extends AsyncTask<T, Void, List<Task>> {
        @Override
        public void onPostExecute(List<Task> result) {
            ListView listView = (ListView) findViewById(R.id.todo_list);
            TasksAdapter adapter = new TasksAdapter(getBaseContext(),result);
            listView.setAdapter(adapter);
        }

        protected List<Task> doQuery() {
            return dbAccess.taskDao.fetchAllTask();
        }
    }

    private class LoadTasks extends BaseTask<Void> {
        @Override
        protected List<Task> doInBackground(Void... params) {
            return (doQuery());
        }
    }

    private class InsertTasks extends BaseTask<Task> {
        @Override
        protected List<Task> doInBackground(Task... values) {
            dbAccess.taskDao.addTask(values[0]);
            return (doQuery());
        }
    }

    private class DeleteTasks extends BaseTask<String> {
        @Override
        protected List<Task> doInBackground(String... values) {
            Task task = new Task();
            task.title = values[0];
            task.id = Integer.parseInt(values[1]);
            dbAccess.taskDao.deleteTask(task);
            return (doQuery());
        }
    }
}
