package com.harputyazilim.todos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by furkan on 4.12.2017.
 */


public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.TodosAdapterViewHolder> {

    private final TodosAdapterOnClickHandler mClickHandler;
    private List<Todo> todos;

    public TodosAdapter(TodosAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    @Override
    public TodosAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.todo_item, parent, false);

        return new TodosAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodosAdapterViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.title.setText(todo.getTitle());
        holder.creator.setText(todo.getCreator());
        holder.assignee.setText(todo.getAssignee());
        holder.date.setText(todo.getDate());
    }

    @Override
    public int getItemCount() {
        if (todos == null) {
            return 0;
        }
        return todos.size();
    }

    public class TodosAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView title;
        public final TextView creator;
        public final TextView assignee;
        public final TextView date;


        public TodosAdapterViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            creator = (TextView) view.findViewById(R.id.creator);
            assignee = (TextView) view.findViewById(R.id.assignee);
            date = (TextView) view.findViewById(R.id.date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(todos.get(getAdapterPosition()));
        }
    }

    public interface TodosAdapterOnClickHandler {
        void onClick(Todo todo);
    }
}
