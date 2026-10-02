package com.example.appmarvel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class JarvisFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private EditText etInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jarvis, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewChat);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        messageList = new ArrayList<>();
        messageList.add(new ChatMessage("SYSTEM", "Good afternoon, Boss. You've spent 15% more on Dining Out this week compared to your 30-day average.", "14:22 [0x-DF]", true));
        messageList.add(new ChatMessage("USER", "JARVIS, log $50 spent on groceries.", "14:23 [0x-1A]", false));

        chatAdapter = new ChatAdapter(messageList);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.scrollToPosition(messageList.size() - 1);

        etInput = view.findViewById(R.id.etChatInput);
        ImageButton btnSend = view.findViewById(R.id.btnSendChat);

        btnSend.setOnClickListener(v -> {
            String text = etInput.getText().toString().trim();
            if (!text.isEmpty()) {
                messageList.add(new ChatMessage("USER", text, "14:25 [0x-2B]", false));
                chatAdapter.notifyItemInserted(messageList.size() - 1);
                recyclerView.smoothScrollToPosition(messageList.size() - 1);
                etInput.setText("");

                String lowerText = text.toLowerCase();
                final String responseText;

                if (lowerText.contains("saldo") || lowerText.contains("balance") || lowerText.contains("quanto tenho")) {
                    responseText = "Seu saldo atual (Savings) é de $1,200.00 (Capital líquido disponível: $2,550.00).";
                } else if (lowerText.contains("gastei") || lowerText.contains("gasto") || lowerText.contains("despesa") || lowerText.contains("mes") || lowerText.contains("mês") || lowerText.contains("quanto")) {
                    responseText = "Você gastou um total de $2,450.00 este mês.";
                } else {
                    responseText = "Comando processado com sucesso, Boss. Atualizando registros financeiros.";
                }

                view.postDelayed(() -> {
                    messageList.add(new ChatMessage("SYSTEM", responseText, "14:25 [0x-2C]", true));
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerView.smoothScrollToPosition(messageList.size() - 1);
                }, 700);
            }
        });

        // Gerenciamento moderno de insets de teclado Edge-to-Edge (Estilo WhatsApp)
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime());
            boolean imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime());

            if (getActivity() != null) {
                View bottomNav = getActivity().findViewById(R.id.bottomNavigation);
                if (bottomNav != null) {
                    bottomNav.setVisibility(imeVisible ? View.GONE : View.VISIBLE);
                }
            }

            // Aplica padding inferior com a altura exata do teclado para empurrar chat e input para cima
            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), imeInsets.bottom);
            return windowInsets;
        });

        return view;
    }
}
