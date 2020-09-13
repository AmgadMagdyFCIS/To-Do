package com.example.to_do.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.to_do.R;
import com.example.to_do.fragments.ListFragment;
import com.example.to_do.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button navigateToListFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, new MainFragment());
        fragmentTransaction.commit();

        navigateToListFragment=findViewById(R.id.button);
        navigateToListFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, ListFragment.newInstance());
                    fragmentTransaction.commit();

            }
        });

    }

}