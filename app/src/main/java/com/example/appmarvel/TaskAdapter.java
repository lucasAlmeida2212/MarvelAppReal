package com.example.appmarvel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<TaskItem> taskList;

    public TaskAdapter(List<TaskItem> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem task = taskList.get(position);
        holder.tvTime.setText(task.getTime());
        holder.tvTitle.setText(task.getTitle());
        holder.tvPriority.setText(task.getPriority());

        // Evitar disparo indeseado do listener durante o bind
        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(task.isCompleted());
        updateTaskUI(holder, task);

        // Clique no card inteiro alterna a tarefa
        holder.itemView.setOnClickListener(v -> {
            boolean newState = !task.isCompleted();
            task.setCompleted(newState);
            holder.checkbox.setChecked(newState);
            updateTaskUI(holder, task);
        });

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            updateTaskUI(holder, task);
        });
    }

    private void updateTaskUI(TaskViewHolder holder, TaskItem task) {
        if (task.isCompleted()) {
            holder.tvTitle.setAlpha(0.6f);
            holder.tvPriority.setText("COMPLETED");
            holder.checkbox.setButtonDrawable(R.drawable.ic_check);
        } else {
            holder.tvTitle.setAlpha(1.0f);
            holder.tvPriority.setText(task.getPriority());
            holder.checkbox.setButtonDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvTitle, tvPriority, tvProgressRemaining;
        CheckBox checkbox;
        LinearLayout layoutProgress;
        ProgressBar progressBar;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTaskTime);
            tvTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvPriority = itemView.findViewById(R.id.tvTaskPriority);
            tvProgressRemaining = itemView.findViewById(R.id.tvProgressRemaining);
            checkbox = itemView.findViewById(R.id.checkboxTask);
            layoutProgress = itemView.findViewById(R.id.layoutProgress);
            progressBar = itemView.findViewById(R.id.progressTask);
        }
    }
}
