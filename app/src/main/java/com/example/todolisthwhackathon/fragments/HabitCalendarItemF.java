package com.example.todolisthwhackathon.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolisthwhackathon.R;
import com.example.todolisthwhackathon.adapters.HabitsListAdapter;

public class HabitCalendarItemF extends Fragment {
    private int pageNumber;

    public static HabitCalendarItemF newInstance(int page) {
        HabitCalendarItemF fragment = new HabitCalendarItemF();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_calendar_item, container, false);

        return view;
    }
}