package com.example.todolisthwhackathon.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolisthwhackathon.R;
import com.example.todolisthwhackathon.adapters.HabitsListAdapter;

public class HabitCalendarF extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_calendar, container, false);

        ViewPager2 pager = view.findViewById(R.id.calendar_habits);
        FragmentStateAdapter pageAdapter = new HabitsListAdapter(getActivity());
        pager.setAdapter(pageAdapter);

        return view;
    }
}