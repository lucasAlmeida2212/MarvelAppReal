package com.example.appmarvel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskDialogFragment extends BottomSheetDialogFragment {

    public interface OnTaskCreatedListener {
        void onTaskCreated(TaskItem taskItem);
    }

    private OnTaskCreatedListener listener;

    public void setOnTaskCreatedListener(OnTaskCreatedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_task, container, false);

        EditText etTitle = view.findViewById(R.id.etMissionTitle);
        TextView tvSelectTime = view.findViewById(R.id.tvSelectTime);
        TextView tvSelectDate = view.findViewById(R.id.tvSelectDate);
        ImageView btnClose = view.findViewById(R.id.btnCloseDialog);
        Button btnInitiate = view.findViewById(R.id.btnInitiateTask);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        final Calendar calendar = Calendar.getInstance();

        tvSelectTime.setOnClickListener(v -> {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view1, selectedHour, selectedMinute) -> {
                String timeStr = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                tvSelectTime.setText(timeStr);
            }, hour, minute, true);
            timePickerDialog.show();
        });

        tvSelectDate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view12, selectedYear, selectedMonth, selectedDay) -> {
                String dateStr = String.format(Locale.getDefault(), "%d.%02d.%02d", selectedYear, selectedMonth + 1, selectedDay);
                tvSelectDate.setText(dateStr);
            }, year, month, day);
            datePickerDialog.show();
        });

        btnClose.setOnClickListener(v -> dismiss());
        btnCancel.setOnClickListener(v -> dismiss());

        btnInitiate.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            if (title.isEmpty()) {
                title = "New Tactical Mission";
            }
            String time = tvSelectTime.getText().toString().trim();
            if (time.isEmpty()) {
                time = "12:00";
            }

            if (listener != null) {
                TaskItem newTask = new TaskItem(time, title, "HIGH PRIORITY", false, false, "");
                listener.onTaskCreated(newTask);
            }
            dismiss();
        });

        return view;
    }
}
