package com.example.to_do.UI.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.to_do.Database.TaskItem;
import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;
import com.example.to_do.RecyclerView.Click;
import com.example.to_do.RecyclerView.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoneFragment extends Fragment implements Click {


    private static String listName ,clearAll;
    private String list ,clear;

    RecyclerView recyclerView;
    private List<TaskItem> recyclerViewItems;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ToDoDBHelper dbHelper;

    public DoneFragment() {
        // Required empty public constructor
    }

    public static DoneFragment newInstance(String param1/*, String param2*/) {
        DoneFragment fragment = new DoneFragment();
        Bundle args = new Bundle();
        args.putString(listName, param1);
        //args.putString(clearAll, param2);
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
        View view = inflater.inflate(R.layout.fragment_done, container, false);
        recyclerViewItems = new ArrayList<>();

        recyclerView = view.findViewById(R.id.doneLists);

        //db
        dbHelper= new ToDoDBHelper(getActivity());
        recyclerViewItems=dbHelper.ReturnTasksOfSpecificList(list,1);



        //recycler view adapter
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),1,recyclerViewItems,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onRecyclerViewClick(int pos) {

    }

    @Override
    public void onClick(int pos) {
        TaskItem taskItem=recyclerViewItems.get(pos);
        dbHelper.FinishTask(taskItem.getName());
        recyclerViewItems.remove(pos);
        recyclerViewAdapter.notifyDataSetChanged();
        getFragmentManager().beginTransaction().replace(R.id.container, ListFragment.newInstance(list)).commit();
    }
}