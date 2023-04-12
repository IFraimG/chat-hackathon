package com.example.todolisthwhackathon.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.todolisthwhackathon.fragments.HabitCalendarF;
import com.example.todolisthwhackathon.fragments.HabitCalendarItemF;

import org.jetbrains.annotations.NotNull;

public class HabitsListAdapter extends FragmentStateAdapter {

    public HabitsListAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HabitCalendarItemF.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
