package com.example.tarottales.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarottales.Model.Message;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.R;
import com.example.tarottales.adapter.MessageAdapter;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AiInteractionActivity extends AppCompatActivity {
    private String apiKey = "AIzaSyAUX4yI7GQlHoI7WQOJ-lcCZbwNf5CSm-s";
    private RecyclerView recyclerView;
    private EditText messageEditText;
    private ImageButton sendButton;
    private ImageView ivBack;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private TarotCard card1, card2, card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ai_interaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get tarot cards from intent
        card1 = (TarotCard) getIntent().getSerializableExtra("card1");
        card2 = (TarotCard) getIntent().getSerializableExtra("card2");
        card3 = (TarotCard) getIntent().getSerializableExtra("card3");

        bindingView();
        bindingAction();
        setupRecyclerView();
        sendInitialMessage();
    }

    private void bindingView() {
        messageList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);
        ivBack = findViewById(R.id.ivBack);
    }

    private void bindingAction() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = messageEditText.getText().toString().trim();
                if (!question.isEmpty()) {
                    addToChat(question, Message.SENT_BY_ME);
                    messageEditText.setText("");
                    callGeminiAPI(question);
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupRecyclerView() {
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
    }

    private void sendInitialMessage() {
        // Create initial context message with tarot cards
        String context = "Bạn là một chuyên gia về bài Tarot. Tôi đã rút được 3 lá bài:\n" +
                "1. " + card1.getName() + ": " + card1.getOverview() + "\n" +
                "2. " + card2.getName() + ": " + card2.getOverview() + "\n" +
                "3. " + card3.getName() + ": " + card3.getOverview() + "\n\n" +
                "Hãy giải thích ý nghĩa tổng quát của 3 lá bài này và cách chúng liên kết với nhau.";
        
        addToChat("Xin chào! Tôi có thể giúp bạn giải thích về bộ bài Tarot bạn vừa rút.", Message.SENT_BY_BOT);
        callGeminiAPI(context);
    }

    private void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    private void addResponse(String response) {
        messageList.remove(messageList.size() - 1); // Remove "Typing..." message
        addToChat(response, Message.SENT_BY_BOT);
    }

    private void callGeminiAPI(String question) {
        // Add "Typing..." message
        messageList.add(new Message("Đang suy nghĩ...", Message.SENT_BY_BOT));

        // Setup Google Gemini model
        GenerativeModel gm = new GenerativeModel("gemini-2.0-flash-exp", apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        // Prepare content for Gemini
        Content content = new Content.Builder()
                .addText(question)
                .build();

        // Create an executor for handling the result
        Executor executor = Executors.newSingleThreadExecutor();

        // Call Gemini API
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                if (result != null && result.getText() != null) {
                    String resultText = result.getText();
                    addResponse(resultText.trim());
                } else {
                    addResponse("Không nhận được phản hồi.");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                addResponse("Không thể tải phản hồi: " + t.getMessage());
            }
        }, executor);
    }
}
