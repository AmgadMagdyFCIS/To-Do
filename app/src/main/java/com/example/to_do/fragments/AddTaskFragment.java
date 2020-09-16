package com.example.to_do.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.to_do.Database.ListClass;
import com.example.to_do.Database.Task;
import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private RadioGroup priorities;
    private RadioButton selectedPriority;
    private EditText date, time , name , description;
    private Spinner tags, duration; // lists
    private Button addTaskButton;
    private ImageButton addListButton;
    private ToDoDBHelper database;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    private Task newTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        database = new ToDoDBHelper(getActivity());
        newTask = new Task();
        linkViewesToCode(view);
        loadSpinnerData();

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.task_date_edit_text:
            {
                final Calendar calender = Calendar.getInstance();
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int month = calender.get(Calendar.MONTH);
                int year = calender.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.setTitle("Select Date");
                datePicker.show();
                break;
            }
            case R.id.task_time_edit_text:
            {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);
                timePicker.setTitle("Select Time");
                timePicker.show();
                break;
            }
            case R.id.add_task_button:
            {
                AddTaskToDBBasedOnFilledFields();
                break;
            }
            case R.id.task_tags_add_button:
            {
                showAlertDialogToAddNewList();
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (view.getId())
        {
            case R.id.task_tags_spinner:
            {
                newTask.setName_of_list(tags.getSelectedItem().toString());
                break;
            }
            case R.id.task_repetead_spinner:
            {
                newTask.setReminder(duration.getSelectedItem().toString());
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void linkViewesToCode(View view)
    {
        priorities = view.findViewById(R.id.task_priorities_radio_group);
        tags = view.findViewById(R.id.task_tags_spinner);
        duration = view.findViewById(R.id.task_repetead_spinner);
        date = view.findViewById(R.id.task_date_edit_text);
        time = view.findViewById(R.id.task_time_edit_text);
        name = view.findViewById(R.id.task_name_edit_text);

        addTaskButton = view.findViewById(R.id.add_task_button);
        addListButton = view.findViewById(R.id.task_tags_add_button);

        linkSelectedPriorityRdioBtnToCode(view);

        addTaskButton.setOnClickListener(this);
        addListButton.setOnClickListener(this);
        tags.setOnItemSelectedListener(this);
        duration.setOnItemSelectedListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    private void linkSelectedPriorityRdioBtnToCode(View view)
    {
        int selectedPriorityID = priorities.getCheckedRadioButtonId();
        if(selectedPriorityID != -1)
        {
            selectedPriority = view.findViewById(selectedPriorityID);
        }
    }


    private void showAlertDialogToAddNewList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("New List");

        final View customLayout = getLayoutInflater().inflate(R.layout.add_list_in_task_fragment, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // send data from the AlertDialog to the Activity
                EditText editText = customLayout.findViewById(R.id.tag_name_edit_text);
                ListClass list = new ListClass(editText.getText().toString());
                database.create_list_with_name_only(list);
                loadSpinnerData();
            }
        });

        builder.setNegativeButton("Cancel" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadSpinnerData() {
        List<String> lists = database.getAllLists();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, lists);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tags.setAdapter(dataAdapter);
    }

    private void AddTaskToDBBasedOnFilledFields()
    {
        checkEmptyFieldsWithToasts();
        if(isThreeRequiredFieldsAreFilled())
        {
            newTask.setName_of_task(name.getText().toString());
            newTask.setPriority(selectedPriority.getText().toString());

            database.create_Task_with_Name_only(newTask);
        }
        else if(isAllFieldsAreFilled())
        {
            newTask.setName_of_task(name.getText().toString());
            newTask.setPriority(selectedPriority.getText().toString());
            newTask.setDate(date.getText().toString());
            newTask.setTime(date.getText().toString());
            newTask.setDescription(description.getText().toString());

            database.create_Task(newTask);
        }
    }

    private void checkEmptyFieldsWithToasts()
    {
        if(name.getText().equals(""))
        {
            Toast.makeText(getActivity().getApplicationContext(),"Please enter task name!",Toast.LENGTH_LONG).show();
        }
        if(selectedPriority.getText().equals(""))
        {
            Toast.makeText(getActivity().getApplicationContext(),"Please select task priority!",Toast.LENGTH_LONG).show();
        }
        if(tags.getSelectedItem().equals(""))
        {
            Toast.makeText(getActivity().getApplicationContext(),"Please select task list!",Toast.LENGTH_LONG).show();

        }
        if(duration.getSelectedItem().equals(""))
        {
            Toast.makeText(getActivity().getApplicationContext(),"Please select task ramainder!",Toast.LENGTH_LONG).show();
        }
    }
    private boolean isThreeRequiredFieldsAreFilled() // task name + list name + priority
    {
        return !name.getText().equals("") && !tags.getSelectedItem().equals("") && !selectedPriority.getText().equals("");
    }

    private boolean isAllFieldsAreFilled()
    {
        return !name.getText().equals("") && !tags.getSelectedItem().equals("") && !selectedPriority.getText().equals("")
                && !date.getText().equals("") && !time.getText().equals("") && !duration.getSelectedItem().equals("") &&
                !description.getText().equals("");
    }




}