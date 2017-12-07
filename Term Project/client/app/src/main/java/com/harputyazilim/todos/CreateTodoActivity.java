package com.harputyazilim.todos;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;

import static com.harputyazilim.todos.MainActivity.IP;
import static com.harputyazilim.todos.MainActivity.PORT;
import static com.harputyazilim.todos.MainActivity.SEND;
import static com.harputyazilim.todos.MainActivity.UPDATE;

public class CreateTodoActivity extends AppCompatActivity {


    private EditText creator;
    private EditText assignee;
    private EditText title;
    private EditText description;
    private EditText deadline;
    private Button create;

    Todo todo = new Todo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_create_todo);
        creator = (EditText) findViewById(R.id.creator_edit);
        assignee = (EditText) findViewById(R.id.assignee_edit);
        title = (EditText) findViewById(R.id.title_edit);
        description = (EditText) findViewById(R.id.description_edit);
        deadline = (EditText) findViewById(R.id.deadline_edit);

        create = (Button) findViewById(R.id.create_todo_activity_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo.setCreator(creator.getText().toString());
                todo.setAssignee(assignee.getText().toString());
                todo.setTitle(title.getText().toString());
                todo.setDescription(description.getText().toString());
                todo.setDeadline(deadline.getText().toString());
                CharSequence s = DateFormat.format("MMMM d, yyyy ", Calendar.getInstance().getTime());
                todo.setLastUpdate(s.toString());

                if (create.getText().toString().equals("UPDATE")) {
                    new Update().execute();
                } else {
                    new Send().execute();
                    todo.setDate(s.toString());
                }

            }
        });

        if (getIntent().getSerializableExtra("todo") != null) {
            todo = (Todo) getIntent().getSerializableExtra("todo");
            creator.setText(todo.getCreator());
            assignee.setText(todo.getAssignee());
            title.setText(todo.getTitle());
            description.setText(todo.getDescription());
            deadline.setText(todo.getDeadline());
            create.setText("UPDATE");
        }


    }

    class Send extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            try {
                Socket clientSocket = new Socket(MainActivity.IP, MainActivity.PORT);
                ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

                outToServer.writeByte(SEND);
                outToServer.flush();
                outToServer.writeObject(todo);
                response = inFromServer.readUTF();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(CreateTodoActivity.this, response, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateTodoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    class Update extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            try {
                Socket clientSocket = new Socket(IP, PORT);
                ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
                outToServer.writeByte(UPDATE);
                outToServer.flush();
                outToServer.writeObject(todo);

                ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
                response = inFromServer.readUTF();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(CreateTodoActivity.this, response, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateTodoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
