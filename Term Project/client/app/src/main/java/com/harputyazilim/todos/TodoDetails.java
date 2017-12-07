package com.harputyazilim.todos;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.harputyazilim.todos.MainActivity.DELETE;
import static com.harputyazilim.todos.MainActivity.IP;
import static com.harputyazilim.todos.MainActivity.PORT;


public class TodoDetails extends Fragment {
    TextView id;
    TextView title;
    TextView creator;
    TextView assignee;
    TextView description;
    TextView date;
    TextView deadline;
    TextView lastUpdate;

    Button back;
    Button update;
    Button close;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TODO = "todo";

    // TODO: Rename and change types of parameters
    private Todo todo;

    public TodoDetails() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TodoDetails newInstance(Todo todo) {
        TodoDetails fragment = new TodoDetails();
        Bundle args = new Bundle();
        args.putSerializable(TODO, todo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todo = (Todo) getArguments().getSerializable(TODO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo_details, container, false);
        id = (TextView) view.findViewById(R.id.id_d);
        title = (TextView) view.findViewById(R.id.title_d);
        creator = (TextView) view.findViewById(R.id.creator_d);
        assignee = (TextView) view.findViewById(R.id.assignee_d);
        description = (TextView) view.findViewById(R.id.description_d);
        date = (TextView) view.findViewById(R.id.date_d);
        lastUpdate = (TextView) view.findViewById(R.id.last_update_d);
        deadline = (TextView) view.findViewById(R.id.deadline_d);

        back = (Button) view.findViewById(R.id.back);
        update = (Button) view.findViewById(R.id.update);
        close = (Button) view.findViewById(R.id.close);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateTodoActivity.class);
                intent.putExtra(TODO, todo);
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Close().execute();
            }
        });

        id.setText("ID: " + todo.getId());
        title.setText("Title: " + todo.getTitle());
        creator.setText("Creator: " + todo.getCreator());
        assignee.setText("Assignee: " + todo.getAssignee());
        description.setText("Description: " + todo.getDescription());
        date.setText("Date: " + todo.getDate());
        lastUpdate.setText("LastUpdate: " + todo.getLastUpdate());
        deadline.setText("Deadline: " + todo.getDeadline());

        return view;
    }

    class Close extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            try {
                Socket clientSocket = new Socket(IP, PORT);
                ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
                outToServer.writeByte(DELETE);
                outToServer.flush();

                outToServer.writeInt(todo.getId());
                outToServer.flush();

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
            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        }
    }
}
