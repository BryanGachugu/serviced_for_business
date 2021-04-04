package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Adapters.ChatListAdapter;
import com.gachugusville.servicedforbusiness.Utils.ChatModel;
import com.gachugusville.servicedforbusiness.Utils.ServiceCategoryList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
        try {
            mFirebaseFirestore.collection("Providers")
                    .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .collection("ChatModel")
                    //TODO we got the ChatList collection, the documents will be the messages, and the document fields will be message values
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            Log.d("Error", Objects.requireNonNull(error.getMessage()));
                        }
                        try {
                            if (value != null) {
                                for (DocumentChange documentChange : value.getDocumentChanges()) {
                                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                        final ChatModel chat = documentChange.getDocument().toObject(ChatModel.class);
                                        modelChats.add(chat);
                                        chatListAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        } catch (NullPointerException e) {
                            Log.d(e.getMessage(), e.getLocalizedMessage());
                        }
                    });
        } catch (Exception e) {
            Log.d("CategoriesError", e.getMessage());
        }
    }

    private void adjustViews() {
        modelChats = new ArrayList<>();
        if (modelChats.isEmpty()){
            //TODO display empty animation
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            Log.d("EmptyCollection", "Good progress");
        }else {
            chatListAdapter = new ChatListAdapter(this, modelChats);
            chat_list_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            chat_list_recycler_view.setHasFixedSize(true);
            chat_list_recycler_view.setAdapter(chatListAdapter);
        }
    }
}