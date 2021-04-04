package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Adapters.ChatListAdapter;
import com.gachugusville.servicedforbusiness.Utils.ChatModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chat_list_recycler_view;
    private List<ChatModel> modelChats;
    private ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        findViewById(R.id.chat_back_btn).setOnClickListener(v -> ChatActivity.super.onBackPressed());
        chat_list_recycler_view = findViewById(R.id.chat_list_recycler_view);

        adjustViews();


    }

    private void adjustViews() {
        modelChats = new ArrayList<>();
        if (modelChats.size() < 0){
            chatListAdapter = new ChatListAdapter(this, modelChats);
            chat_list_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            chat_list_recycler_view.setHasFixedSize(true);
            chat_list_recycler_view.setAdapter(chatListAdapter);
        }
    }
}