package com.example.to_do.UI.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.Database.TaskItem;
import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;
import com.example.to_do.RecyclerView.Click;
import com.example.to_do.RecyclerView.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements Click {


    ImageButton searchButton;
    EditText taskName;
    RecyclerView recyclerView;
    private List<TaskItem> recyclerViewItems;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ToDoDBHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        taskName = view.findViewById(R.id.taskNameSearch);
        searchButton = view.findViewById(R.id.searchButton);
        recyclerViewItems = new ArrayList<>();

        recyclerView = view.findViewById(R.id.tasksList);

        //db
        dbHelper = new ToDoDBHelper(getActivity());
        recyclerViewItems = new ArrayList<>();


        //recycler view adapter
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), 1, recyclerViewItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gitTask();

            }
        });
        return view;
    }

    public void gitTask() {

        Cursor curs = dbHelper.fetchTask(taskName.getText().toString());
        if (curs != null) {
            recyclerViewItems.clear();
            while (!curs.isAfterLast()) {
                recyclerViewItems.add(new TaskItem(curs.getString(0), curs.getString(1), curs.getString(2), curs.getString(3), curs.getString(4), curs.getString(5), curs.getString(6),curs.getInt(7)));
                curs.moveToNext();
            }
        }
        recyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRecyclerViewClick(int pos) {
        TaskItem taskItem = recyclerViewItems.get(pos);
        Toast.makeText(getActivity(), taskItem.getName(), Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction().replace(R.id.container, AddTaskFragment.newInstance(taskItem.getName(), taskItem.getListName())).commit();
    }

    @Override
    public void onDeleteButtonClick(int pos) {
        TaskItem taskItem = recyclerViewItems.get(pos);
        dbHelper.deleteTask(taskItem.getName());
        recyclerViewItems.remove(pos);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int pos) {
        TaskItem taskItem = recyclerViewItems.get(pos);
        dbHelper.FinishTask(taskItem.getName());
        if (taskItem.getDone() == 0)
            taskItem.setDone(1);
        else
            taskItem.setDone(0);
        recyclerViewItems.remove(pos);
        recyclerViewAdapter.notifyDataSetChanged();

    }
}
