package com.example.to_do.Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<ListItem> tasks;

    public RecyclerViewAdapter(Context context, List<ListItem> tasks) {
        this.context = context;
        this.tasks = tasks;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        ListItem recyclerViewItem = tasks.get(position);
        holder.name.setText(recyclerViewItem.getName());
        holder.numberOfTasks.setText(String.valueOf(recyclerViewItem.getNumberOfTasks()));
        holder.description.setText(recyclerViewItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,numberOfTasks,description;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            numberOfTasks = itemView.findViewById(R.id.number_of_tasks);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition() != -1){
                        ListItem task = tasks.get(getAdapterPosition());
                        Toast.makeText(context,task.getName(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}
