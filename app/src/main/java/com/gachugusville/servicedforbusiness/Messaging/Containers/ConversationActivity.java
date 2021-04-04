package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.ChatModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CircleImageView profile_iv_conversations;
    private TextView txt_customer_name, txt_online_status;
    private EditText edt_input_msg;
    private ImageButton btn_send_msg;
    private RecyclerView messages_recycler_view;

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

        ChatModel chatModel = new ChatModel();
        chatModel.


    }
}