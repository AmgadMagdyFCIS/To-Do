package com.example.to_do.UI.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.to_do.Database.ListItem;
import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;


public class AddListFragment extends Fragment {

    private ToDoDBHelper toDoDBHelper;
    private Button addNewList,cancelNewList;
    TextView create;
    private EditText name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_list, container, false);
        create = view.findViewById(R.id.Create);

        name = view.findViewById(R.id.new_list_name);

        addNewList = view.findViewById(R.id.add_new_list);
        cancelNewList = view.findViewById(R.id.cancel_new_list);

        toDoDBHelper= new ToDoDBHelper(getActivity());

        addNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItem l = new ListItem(name.getText().toString(),0);
                toDoDBHelper.create_list(l);
                getFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();
                Toast.makeText(getContext()," Added "+name.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        cancelNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();
            }
        });

        return view;
    }


}