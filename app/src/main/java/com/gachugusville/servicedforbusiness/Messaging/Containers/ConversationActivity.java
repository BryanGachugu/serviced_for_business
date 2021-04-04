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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Messaging.Notification.ApiService;
import com.gachugusville.servicedforbusiness.Messaging.Notification.Client;
import com.gachugusville.servicedforbusiness.Utils.ChatModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

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
            if (message.isEmpty()){
                Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
            }else {
                sendMessage(message);
            }
        });

        edt_input_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0){
                    checkTypingStatus("noOne");
                }else {
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
        DocumentReference customerDocRef = FirebaseFirestore.getInstance().collection("Users").document(customerUid)
                .collection("ChatModel").document(myUid);
        HashMap<String, Object> messageDetails = new HashMap<>();
        messageDetails.put("")
    }
}