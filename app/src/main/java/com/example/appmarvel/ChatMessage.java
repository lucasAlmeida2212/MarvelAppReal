package com.example.appmarvel;

public class ChatMessage {
    private String sender; // "SYSTEM" or "USER"
    private String text;
    private String time;
    private boolean isSystem;

    public ChatMessage(String sender, String text, String time, boolean isSystem) {
        this.sender = sender;
        this.text = text;
        this.time = time;
        this.isSystem = isSystem;
    }

    public String getSender() { return sender; }
    public String getText() { return text; }
    public String getTime() { return time; }
    public boolean isSystem() { return isSystem; }
}
