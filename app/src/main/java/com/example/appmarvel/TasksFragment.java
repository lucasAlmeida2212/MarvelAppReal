package com.example.appmarvel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<TaskItem> taskList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        taskList = new ArrayList<>();
        taskList.add(new TaskItem("08:00", "Morning Prep", "HIGH PRIORITY", true, false, ""));
        taskList.add(new TaskItem("10:00", "Deep Work session", "IN PROGRESS", false, true, "42m REMAINING"));
        taskList.add(new TaskItem("13:00", "Physiology Calibration (Workout)", "MEDIUM PRIORITY", false, false, ""));
        taskList.add(new TaskItem("16:00", "Armor Diagnostics & Firmware Update", "HIGH PRIORITY", false, false, ""));
        taskList.add(new TaskItem("19:00", "Stark Industries Board Briefing", "MEDIUM PRIORITY", false, false, ""));

        // Ordenar inicialmente por horário
        taskList.sort(Comparator.comparing(TaskItem::getTime));

        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fabAddTask);
        fab.setOnClickListener(v -> {
            AddTaskDialogFragment dialog = new AddTaskDialogFragment();
            dialog.setOnTaskCreatedListener(taskItem -> {
                taskList.add(taskItem);
                taskList.sort(Comparator.comparing(TaskItem::getTime));
                taskAdapter.notifyDataSetChanged();
                int position = taskList.indexOf(taskItem);
                recyclerView.scrollToPosition(position);
            });
            dialog.show(getParentFragmentManager(), "AddTaskDialog");
        });

        return view;
    }
}
