package com.example.appmarvel;

public class TaskItem {
    private String time;
    private String title;
    private String priority;
    private boolean isCompleted;
    private boolean isInProgress;
    private String progressText;

    public TaskItem(String time, String title, String priority, boolean isCompleted, boolean isInProgress, String progressText) {
        this.time = time;
        this.title = title;
        this.priority = priority;
        this.isCompleted = isCompleted;
        this.isInProgress = isInProgress;
        this.progressText = progressText;
    }

    public String getTime() { return time; }
    public String getTitle() { return title; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    public boolean isInProgress() { return isInProgress; }
    public String getProgressText() { return progressText; }
}
