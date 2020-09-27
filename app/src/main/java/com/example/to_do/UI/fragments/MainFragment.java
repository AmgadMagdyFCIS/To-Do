package com.example.to_do.UI.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.to_do.Database.ListItem;
import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;
import com.example.to_do.RecyclerView.Click;
import com.example.to_do.RecyclerView.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements Click {

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
            recyclerViewItems.add(new ListItem(cur.getString(0), cur.getInt(1)));
            cur.moveToNext();
        }

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), recyclerViewItems, 0, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new AddListFragment()).commit();
            }
        });

        return view;
    }


    private List<ListItem> setTasks() {
        List<ListItem> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new ListItem("Anas" + i, (7 + i)));
        }

        return list;
    }

    @Override
    public void onRecyclerViewClick(int pos) {

        ListItem listItem = recyclerViewItems.get(pos);
        Toast.makeText(getActivity(),listItem.getName(),Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction().replace(R.id.container, ListFragment.newInstance(listItem.getName())).commit();
    }

    @Override
    public void onDeleteButtonClick(int pos) {

    }

    @Override
    public void onClick(int pos) {

    }
}