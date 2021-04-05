package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Messaging.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CircleImageView profile_iv_conversations;
    private TextView txt_customer_name, txt_online_status;
    private EditText edt_input_msg;
    private ImageButton btn_send_msg;
    private RecyclerView messages_recycler_view;
    private String customerUid;
    private MessagesList messagesList;


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
        messagesList = findViewById(R.id.messagesList);

        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(myUid, (imageView, url, payload) -> Picasso.get().load(url).into(imageView));
        messagesList.setAdapter(adapter);
        adapter.setOnMessageLongClickListener(message -> Toast.makeText(ConversationActivity.this, "message to delete", Toast.LENGTH_SHORT).show());

    }
}