package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Messaging.Notification.ApiService;
import com.gachugusville.servicedforbusiness.Messaging.Notification.Client;
import com.gachugusville.servicedforbusiness.Messaging.Notification.Data;
import com.gachugusville.servicedforbusiness.Messaging.Notification.Response;
import com.gachugusville.servicedforbusiness.Messaging.Notification.Sender;
import com.gachugusville.servicedforbusiness.Messaging.Notification.Token;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class ConversationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CircleImageView profile_iv_conversations;
    private TextView txt_customer_name, txt_online_status;
    private EditText edt_input_msg;
    private ImageButton btn_send_msg;
    private RecyclerView messages_recycler_view;
    private String customerUid;
    private String myUid;
    private String customerImage;
    private FirebaseAuth firebaseAuth;

    ApiService apiService;
    boolean notify = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.gachugusville.development.servicedforbusiness.R.layout.activity_conversation);

        toolbar = findViewById(R.id.toolbar);
        profile_iv_conversations = findViewById(R.id.profile_iv_conversations);
        txt_customer_name = findViewById(R.id.txt_customer_name);
        txt_online_status = findViewById(R.id.txt_online_status);
        edt_input_msg = findViewById(R.id.edt_input_msg);
        btn_send_msg = findViewById(R.id.btn_send_msg);
        messages_recycler_view = findViewById(R.id.messages_recycler_view);
        firebaseAuth = FirebaseAuth.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messages_recycler_view.setHasFixedSize(true);
        messages_recycler_view.setLayoutManager(linearLayoutManager);

        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(ApiService.class);

        Intent intent = getIntent();
        customerUid = intent.getStringExtra("customerUid");

        btn_send_msg.setOnClickListener(v -> {
            notify = true;
            String message = edt_input_msg.getText().toString().trim();
            if (message.isEmpty()) {
                Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
            } else {
                sendMessage(message);
            }
        });

        edt_input_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    checkTypingStatus("noOne");
                } else {
                    checkTypingStatus(customerUid);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void sendMessage(String message) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        // We go to the user collection
        // we go to the user we are texting
        //in his fields, there is
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> messageDetails = new HashMap<>();
        messageDetails.put("sender", myUid);
        messageDetails.put("receiver", customerUid);
        messageDetails.put("message", message);
        messageDetails.put("timestamp", timeStamp);
        messageDetails.put("isSeen", false);
        databaseReference.child("Chats").push().setValue(messageDetails);
        edt_input_msg.setText("");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sendNotification(customerUid, Provider.getInstance().getUser_name(), message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
    }

    private void sendNotification(String customerUid, String user_name, String message) {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(customerUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Token token = dataSnapshot.getValue(Token.class);
                    Data data = new Data(myUid, user_name + " : " + message, "New message", customerUid, R.drawable.test);
                    if (token != null) {
                        Sender sender = new Sender(data, token.getToken());
                        apiService.sendNotification(sender)
                                .enqueue(new Callback<Response>() {
                                    @Override
                                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                        Toast.makeText(ConversationActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Response> call, Throwable t) {

                                    }
                                });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}