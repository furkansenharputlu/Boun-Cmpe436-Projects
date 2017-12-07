package com.harputyazilim.todos;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodosAdapter.TodosAdapterOnClickHandler {
    public static final String IP = "192.168.72.196";
    public static final int PORT = 6789;
    public static final int GET = 1;
    public static final int SEND = 2;
    public static final int UPDATE = 3;
    public static final int DELETE = 4;

    private RecyclerView recyclerView;
    private TodosAdapter todosAdapter;
    private SwipeRefreshLayout swipe;
    private Button createTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTodo = (Button) findViewById(R.id.create_todo);
        recyclerView = (RecyclerView) findViewById(R.id.todos);
        swipe= (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        todosAdapter = new TodosAdapter(this);
        recyclerView.setAdapter(todosAdapter);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Get().execute();
            }
        });

        createTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTodoActivity.class);
                startActivity(intent);
            }
        });
        new Get().execute();
    }

    @Override
    public void onClick(Todo todo) {
        TodoDetails todoDetails = new TodoDetails().newInstance(todo);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                .replace(R.id.activity_main, todoDetails)
                .addToBackStack(null)
                .commit();
    }


    class Get extends AsyncTask<String, Void, List<Todo>> {

        @Override
        protected List<Todo> doInBackground(String... params) {
            List<Todo> todos = new ArrayList<>();
            try {
                Socket clientSocket = new Socket(IP, PORT);
                ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());

                outToServer.writeByte(GET);
                outToServer.flush();


                ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
                int size = inFromServer.readInt();
                for (int i = 0; i < size; i++) {
                    todos.add((Todo) inFromServer.readObject());
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return todos;
        }

        @Override
        protected void onPostExecute(List<Todo> todos) {
            todosAdapter.setTodos(todos);
            swipe.setRefreshing(false);
        }
    }

}
