package com.example.to_do.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.to_do.Database.ListItem;
import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;
import com.example.to_do.Recyclers.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    RecyclerView recyclerView;
    private List<ListItem> recyclerViewItems;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ToDoDBHelper dbHelper;
    FloatingActionButton addTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        addTask = view.findViewById(R.id.add_task_float);
        recyclerViewItems = new ArrayList<>();

        recyclerView = view.findViewById(R.id.lists);

        dbHelper = new ToDoDBHelper(getActivity());

        Cursor cur = dbHelper.fetchAllLists();

        while (!cur.isAfterLast()) {
            recyclerViewItems.add(new ListItem(cur.getString(0), cur.getInt(1), cur.getString(2)));
            cur.moveToNext();
        }

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), recyclerViewItems, 0);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.drawer, new AddTaskFragment(), "fragment_screen");
                ft.commit();
            }
        });

        return view;
    }


    private List<ListItem> setTasks() {
        List<ListItem> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new ListItem("Anas" + i, (7 + i), "high" + i));
        }

        return list;
    }

}