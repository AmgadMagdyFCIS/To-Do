package com.example.to_do.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.to_do.R;
import com.example.to_do.Recyclers.RecyclerViewAdapter;
import com.example.to_do.activity.MainActivity;
import com.example.to_do.viewpager.ViewPagerAdapter;
import com.example.to_do.viewpager.ViewPagerItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {


    ViewPager viewPager;
    TabLayout tabs;
    FloatingActionButton fab;
    Toolbar toolbar;

    private static String listName ;
    private String list;
    private RecyclerViewAdapter recyclerViewAdapter;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String param1) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(listName, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            list = getArguments().getString(listName);
;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // toolbar
        toolbar = view.findViewById(R.id.toolbarOfListFragment);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);


        //view pager
        viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), getViewPagerItems());
        viewPager.setAdapter(viewPagerAdapter);

        //tab layout
        tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);








        //floating button
        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "hiiiiiii", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                getFragmentManager().beginTransaction().replace(R.id.container,new SearchFragment()).commit();
                return true;
            case R.id.sort:


                return true;
            case R.id.sortByPriority:


                return true;
            case R.id.sortAlphabetically:


                return true;
            case R.id.sortByDate:


                return true;

            case R.id.theme:


                return true;
            case R.id.clearAll:

                return true;
            case R.id.delete:

                return true;
        }
        return false;
    }

    private List<ViewPagerItem> getViewPagerItems() {
        List<ViewPagerItem> viewPagerItems = new ArrayList<>();
        viewPagerItems.add(new ViewPagerItem(getString(R.string.tasks), new TasksFragment()));
        viewPagerItems.add(new ViewPagerItem(getString(R.string.done), new DoneFragment()));
        return viewPagerItems;
    }


}