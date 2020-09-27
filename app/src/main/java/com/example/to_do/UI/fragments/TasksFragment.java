package com.example.to_do.UI.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.to_do.Database.TaskItem;
import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;
import com.example.to_do.RecyclerView.Click;
import com.example.to_do.RecyclerView.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class TasksFragment extends Fragment implements Click {

    private static String listName ,clearAll;
    private String list ,clear;

    RecyclerView recyclerView;
    private List<TaskItem> recyclerViewItems;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ToDoDBHelper dbHelper;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(String param1/*, String param2*/) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString(listName, param1);
       // args.putString(clearAll, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            list = getArguments().getString(listName);
            //clear = getArguments().getString(clearAll);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerViewItems = new ArrayList<>();

        recyclerView = view.findViewById(R.id.tasksList);

        //db
        dbHelper= new ToDoDBHelper(getActivity());
        recyclerViewItems=dbHelper.returnTasksOfSpecificList(list,0);



        //recycler view adapter
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),1,recyclerViewItems , this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onRecyclerViewClick(int pos) {
        TaskItem taskItem=recyclerViewItems.get(pos);
        Toast.makeText(getActivity(),taskItem.getName(),Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction().replace(R.id.container, AddTaskFragment.newInstance(taskItem.getName(),list)).commit();
    }

    @Override
    public void onDeleteButtonClick(int pos) {
        TaskItem taskItem=recyclerViewItems.get(pos);
        dbHelper.deleteTask(taskItem.getName());
        recyclerViewItems.remove(pos);
        recyclerViewAdapter.notifyDataSetChanged();
        getFragmentManager().beginTransaction().replace(R.id.container, ListFragment.newInstance(list)).commit();
    }

    @Override
    public void onClick(int pos) {
        TaskItem taskItem=recyclerViewItems.get(pos);
        dbHelper.FinishTask(taskItem.getName());
        taskItem.setDone(1);
        dbHelper.decrease(list);
        recyclerViewItems.remove(pos);
        recyclerViewAdapter.notifyDataSetChanged();
        getFragmentManager().beginTransaction().replace(R.id.container, ListFragment.newInstance(list)).commit();
    }
}
