package com.example.to_do.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.to_do.Database.ListClass;
import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;
import com.example.to_do.Recyclers.ListItem;
import com.example.to_do.activity.Login;
import com.example.to_do.activity.MainActivity;


public class AddListFragment extends Fragment {

    private ToDoDBHelper toDoDBHelper;
    private TextView create,toDo,addNewList,cancelNewList;
    private EditText name,description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_list, container, false);
        create = view.findViewById(R.id.Create);
        toDo = view.findViewById(R.id.to_do);

        name = view.findViewById(R.id.new_list_name);
        description = view.findViewById(R.id.new_list_description);

        addNewList = view.findViewById(R.id.add_new_list);
        cancelNewList = view.findViewById(R.id.cancel_new_list);

        toDoDBHelper= new ToDoDBHelper(getActivity());

        addNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListClass l = new ListClass(name.getText().toString(),0,description.getText().toString());
                toDoDBHelper.create_list(l);
                Intent Main = new Intent(getActivity(), MainActivity.class);
                startActivity(Main);
                Toast.makeText(getContext()," Added "+name.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        cancelNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Main = new Intent(getActivity(), MainActivity.class);
                startActivity(Main);
            }
        });

        return view;
    }
}