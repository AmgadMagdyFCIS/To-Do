package com.example.to_do.Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.Database.ListItem;
import com.example.to_do.Database.TaskItem;
import com.example.to_do.R;
import com.example.to_do.fragments.ListFragment;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<TaskItem> tasksList;
    private List<ListItem> listsList;
    private Click click;
    private int type;

    public RecyclerViewAdapter(Context context, int type,List<TaskItem> tasks , Click click) {
        this.context = context;
        this.tasksList = tasks;
        this.click=click;
        this.type=type;
    }
    public RecyclerViewAdapter(Context context, List<ListItem> lists ,int type , Click click) {
        this.context = context;
        this.listsList = lists;
        this.click=click;
        this.type=type;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(type==0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        }
        else
            view =LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        if (type==0) {
            ListItem recyclerViewListItem = listsList.get(position);
            holder.listName.setText(recyclerViewListItem.getName());
            holder.numberOfTasks.setText(String.valueOf(recyclerViewListItem.getNumberOfTasks()));
        }
        else
        {
            TaskItem recyclerViewTaskItem = tasksList.get(position);
            holder.taskName.setText(recyclerViewTaskItem.getName());

        }
    }

    @Override
    public int getItemCount() {
        if(type==0) {
            return listsList.size();
        }
        else
        {
            return tasksList.size();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView listName,numberOfTasks;

        TextView taskName;


        public MyViewHolder(View itemView) {
            super(itemView);
            if(type==0) {
                listName = itemView.findViewById(R.id.name);
                numberOfTasks = itemView.findViewById(R.id.number_of_tasks);


            }
            else
            {
                taskName=itemView.findViewById(R.id.taskName);

            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != -1) {
                        if(type==0) {

                            click.onRecyclerViewClick(getAdapterPosition());
                        }
                        else
                        {
                            click.onRecyclerViewClick(getAdapterPosition());
                        }
                    }
                }
            });
        }
    }


}
