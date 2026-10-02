package com.example.appmarvel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SYSTEM = 0;
    private static final int VIEW_TYPE_USER = 1;

    private final List<ChatMessage> messageList;

    public ChatAdapter(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).isSystem() ? VIEW_TYPE_SYSTEM : VIEW_TYPE_USER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SYSTEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_system, parent, false);
            return new SystemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user, parent, false);
            return new UserViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        View itemView = holder.itemView;

        // Animação suave de fade-in para a mensagem aparecer na tela
        itemView.setAlpha(0f);
        itemView.animate().alpha(1f).setDuration(350).start();

        if (holder instanceof SystemViewHolder) {
            SystemViewHolder sysHolder = (SystemViewHolder) holder;
            sysHolder.tvMessage.setText(message.getText());
            sysHolder.tvTime.setText(message.getTime());
        } else if (holder instanceof UserViewHolder) {
            UserViewHolder userHolder = (UserViewHolder) holder;
            userHolder.tvMessage.setText(message.getText());
            userHolder.tvTime.setText(message.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class SystemViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;

        public SystemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvSystemMessage);
            tvTime = itemView.findViewById(R.id.tvSystemTime);
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvUserMessage);
            tvTime = itemView.findViewById(R.id.tvUserTime);
        }
    }
}
