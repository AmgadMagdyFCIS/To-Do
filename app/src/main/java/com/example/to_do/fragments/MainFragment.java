package com.example.to_do.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;
import com.example.to_do.Recyclers.ListItem;
import com.example.to_do.Recyclers.RecyclerViewAdapter;
import com.example.to_do.activity.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    RecyclerView recyclerView;
    private List<ListItem> recyclerViewItems;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ToDoDBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerViewItems = new ArrayList<>();

        recyclerView = view.findViewById(R.id.lists);

        dbHelper= new ToDoDBHelper(getActivity());

        Cursor cur =dbHelper.fetchAllLists();

        while (!cur.isAfterLast()) {
            recyclerViewItems.add(new ListItem(cur.getString(0), cur.getInt(1), cur.getString(2)));
            cur.moveToNext();
        }

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), recyclerViewItems);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);


        return view;
    }



    private List<ListItem> setTasks(){
        List<ListItem> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(new ListItem("Anas"+i,(7+i),"high"+i));
        }

        return list;
    }

}