package com.example.to_do.UI.fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.to_do.Database.ListItem;
import com.example.to_do.Database.TaskItem;
import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;

import java.util.Calendar;
import java.util.List;


public class AddTaskFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    private static final String getTaskName = "taskName";
    private static final String getListName = "ListName";
    private String taskName, listName;

    private RadioGroup priorities;
    private RadioButton priorityA, priorityB, priorityC, priorityD;
    private EditText date, time, name, description;
    private Spinner tags, duration; // lists
    private Button addTaskButton;
    private ImageButton addListButton;
    private ToDoDBHelper database;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;
    private int Hour, Minute, Day, Month, Year;
    private String priorityName = "";


    private TaskItem newTaskItem, oldTaskItem;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putString(getTaskName, param1);
        args.putString(getListName, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskName = getArguments().getString(getTaskName);
            listName = getArguments().getString(getListName);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        database = new ToDoDBHelper(getActivity());
        newTaskItem = new TaskItem();

        gitTask();
        linkViewesToCode(view);
        loadSpinnerData();

        return view;
    }


    public void gitTask() {

        Cursor curs = database.fetchTask(taskName);
        if (curs != null) {
            while (!curs.isAfterLast()) {
                oldTaskItem = new TaskItem(curs.getString(0), curs.getString(1), curs.getString(2), curs.getString(3), curs.getString(4), curs.getString(5), curs.getString(6),curs.getInt(7));
                curs.moveToNext();
            }
        }
    }


    @Override
    public void onClick(View view) {
        int year, day, month;
        switch (view.getId()) {
            case R.id.task_date_edit_text: {
                final Calendar now = Calendar.getInstance();

                day = now.get(Calendar.DAY_OF_MONTH);
                month = now.get(Calendar.MONTH);
                year = now.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                Day = dayOfMonth;
                                Month = monthOfYear;
                                Year = year;
                            }
                        }, year, month, day);
                datePicker.setTitle("Select Date");
                datePicker.show();
                break;
            }
            case R.id.task_time_edit_text: {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                        Hour = selectedHour;
                        Minute = selectedMinute;
                    }
                }, hour, minute, false);
                timePicker.setTitle("Select Time");
                timePicker.show();
                break;
            }
            case R.id.add_task_button: {
                AddTaskToDBBasedOnFilledFields(view);
                if (oldTaskItem != null) {
                    String Date = oldTaskItem.getDate().toString();
                    String[] date = Date.split("/");
                    String Time = oldTaskItem.getTime().toString();
                    String[] time = Time.split(":");
                    System.out.println(date[0] + " " + date[1] + " " + date[2] + " " + time[0] + " " + time[1]);

                    Year = Integer.valueOf(date[2]);
                    Month = Integer.valueOf(date[1]);
                    Day = Integer.valueOf(date[0]);

                    Hour = Integer.valueOf(time[0]);
                    Minute = Integer.valueOf(time[1]);

                    Calendar current = Calendar.getInstance();
                    Calendar cal = Calendar.getInstance();
                    cal.set(Year,
                            Month,
                            Day,
                            Hour,
                            Minute,
                            00);

                    if (cal.compareTo(current) <= 0) {
//The set Date/Time already passed
                        Toast.makeText(getContext(), "Invalid Date/Time", Toast.LENGTH_LONG).show();
                    } else {
                        scheduleNotification(getNotification(), cal, duration.getSelectedItem().toString());
                        getFragmentManager().beginTransaction().replace(R.id.container, ListFragment.newInstance(tags.getSelectedItem().toString())).commit();
                    }
                } else {
                    Calendar current = Calendar.getInstance();
                    Calendar cal = Calendar.getInstance();
                    cal.set(Year,
                            Month,
                            Day,
                            Hour,
                            Minute,
                            00);

                    if (cal.compareTo(current) <= 0) {
//The set Date/Time already passed
                        Toast.makeText(getContext(), "Invalid Date/Time", Toast.LENGTH_LONG).show();
                    } else {
                        scheduleNotification(getNotification(), cal, duration.getSelectedItem().toString());
                        getFragmentManager().beginTransaction().replace(R.id.container, ListFragment.newInstance(tags.getSelectedItem().toString())).commit();
                    }
                }
                break;
            }
            case R.id.task_tags_add_button: {
                showAlertDialogToAddNewList();
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (view.getId()) {
            case R.id.task_tags_spinner: {
                newTaskItem.setListName(tags.getSelectedItem().toString());
                break;
            }
            case R.id.task_repetead_spinner: {
                newTaskItem.setReminder(duration.getSelectedItem().toString());
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void linkViewesToCode(View view) {
        priorities = view.findViewById(R.id.task_priorities_radio_group);
        tags = view.findViewById(R.id.task_tags_spinner);
        duration = view.findViewById(R.id.task_repetead_spinner);
        date = view.findViewById(R.id.task_date_edit_text);
        time = view.findViewById(R.id.task_time_edit_text);
        name = view.findViewById(R.id.task_name_edit_text);
        description = view.findViewById(R.id.task_description_edit_text);
        addTaskButton = view.findViewById(R.id.add_task_button);
        addListButton = view.findViewById(R.id.task_tags_add_button);
        priorityA = view.findViewById(R.id.task_priority_A);
        priorityB = view.findViewById(R.id.task_priority_B);
        priorityC = view.findViewById(R.id.task_priority_C);
        priorityD = view.findViewById(R.id.task_priority_D);


        if (oldTaskItem != null) {

            switch (oldTaskItem.getPriority()) {
                case "Urgent":
                    priorities.check(R.id.task_priority_A);
                    break;
                case "High":
                    priorities.check(R.id.task_priority_B);
                    break;
                case "Normal":
                    priorities.check(R.id.task_priority_C);
                    break;
                case "Low":
                    priorities.check(R.id.task_priority_D);
                    break;
            }
            switch (oldTaskItem.getReminder()) {
                case "5 minutes":
                    duration.setSelection(0);
                    break;
                case "10 minutes":
                    duration.setSelection(1);
                    break;
                case "15 minutes":
                    duration.setSelection(2);
                    break;
                case "30 minutes":
                    duration.setSelection(3);
                    break;
                case "1 hours":
                    duration.setSelection(4);
                    break;
                case "2 hours":
                    duration.setSelection(5);
                    break;
            }

            date.setText(oldTaskItem.getDate());
            time.setText(oldTaskItem.getTime());
            name.setText(oldTaskItem.getName());
            description.setText(oldTaskItem.getDescription());


        }

        addTaskButton.setOnClickListener(this);
        addListButton.setOnClickListener(this);
        tags.setOnItemSelectedListener(this);
        duration.setOnItemSelectedListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);


    }

    private void linkSelectedPriorityRdioBtnToCode(View view) {
        if (priorityA.isChecked()) {
            priorityName = priorityA.getText().toString();
        } else if (priorityB.isChecked()) {
            priorityName = priorityB.getText().toString();
        } else if (priorityC.isChecked()) {
            priorityName = priorityC.getText().toString();
        } else if (priorityD.isChecked()) {
            priorityName = priorityD.getText().toString();
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
                ListItem list = new ListItem(editText.getText().toString());
                database.create_list(list);
                loadSpinnerData();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lists);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tags.setAdapter(dataAdapter);
        for (int i = 0; i < tags.getCount(); i++) {
            if (tags.getItemAtPosition(i).toString().equals(listName)) {
                tags.setSelection(i);
            }
        }
    }

    private void AddTaskToDBBasedOnFilledFields(View view) {
        linkSelectedPriorityRdioBtnToCode(view);

        checkEmptyFieldsWithToasts();
        if (isAllFieldsAreFilled()) {
            if (oldTaskItem == null) {
                newTaskItem.setName(name.getText().toString());
                newTaskItem.setPriority(priorityName);
                newTaskItem.setDate(date.getText().toString());
                newTaskItem.setTime(time.getText().toString());
                newTaskItem.setDescription(description.getText().toString());
                newTaskItem.setListName(tags.getSelectedItem().toString());
                newTaskItem.setReminder(duration.getSelectedItem().toString());
                database.create_Task(newTaskItem);
            } else {
                oldTaskItem.setName(name.getText().toString());
                oldTaskItem.setPriority(priorityName);
                oldTaskItem.setDate(date.getText().toString());
                oldTaskItem.setTime(time.getText().toString());
                oldTaskItem.setDescription(description.getText().toString());
                oldTaskItem.setListName(tags.getSelectedItem().toString());
                oldTaskItem.setReminder(duration.getSelectedItem().toString());
                database.editTask(taskName, oldTaskItem);

            }
        } else if (isThreeRequiredFieldsAreFilled()) {
            newTaskItem.setName(name.getText().toString());
            newTaskItem.setPriority(priorityName);

            database.create_Task(newTaskItem);
        }
    }

    private void checkEmptyFieldsWithToasts() {
        if (name.getText().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter task name!", Toast.LENGTH_LONG).show();
        }
        if (priorityName.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please select task priority!", Toast.LENGTH_LONG).show();
        }
        if (tags.getSelectedItem().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please select task list!", Toast.LENGTH_LONG).show();

        }
        if (duration.getSelectedItem().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please select task ramainder!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isThreeRequiredFieldsAreFilled() // task name + list name + priority
    {
        return !name.getText().equals("") && !tags.getSelectedItem().equals("") && !priorityName.equals("");
    }

    private boolean isAllFieldsAreFilled() {
        return !name.getText().equals("") && !tags.getSelectedItem().equals("") && !priorityName.equals("")
                && !date.getText().equals("") && !time.getText().equals("") && !duration.getSelectedItem().equals("") &&
                !description.getText().equals("");
    }

    private void scheduleNotification(Notification notification, Calendar cal, String interval) {

        Intent notificationIntent = new Intent(getContext(), com.example.to_do.Notification.class);
        notificationIntent.putExtra(com.example.to_do.Notification.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(com.example.to_do.Notification.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        long timeBeforeNotification = 0;
        switch (interval) {
            case "5 minutes":
                timeBeforeNotification = 5 * 60000;
                break;
            case "10 minutes":
                timeBeforeNotification = 10 * 60000;
                break;
            case "15 minutes":
                timeBeforeNotification = 15 * 60000;
                break;
            case "30 minutes":
                timeBeforeNotification = 30 * 60000;
                break;
            case "1 hours":
                timeBeforeNotification = 60 * 60000;
                break;
            case "2 hours":
                timeBeforeNotification = 2 * 60 * 60000;
                break;
            default:
                break;
        }

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - timeBeforeNotification, pendingIntent);
    }

    private Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), default_notification_channel_id);
        builder.setContentTitle(name.getText().toString());
        builder.setContentText(description.getText().toString());
        builder.setSmallIcon(R.drawable.logo);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }


}