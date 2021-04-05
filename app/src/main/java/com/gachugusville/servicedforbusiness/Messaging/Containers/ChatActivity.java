package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Constants;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.getstream.sdk.chat.ChatUI;
import com.google.firebase.auth.FirebaseAuth;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.logger.ChatLogLevel;
import io.getstream.chat.android.client.models.User;
import io.getstream.chat.android.livedata.ChatDomain;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChatClient chatClient = new ChatClient.Builder(Constants.STREAM_API_KEY, this)
                .logLevel(ChatLogLevel.ALL)
                .build();

        // Step 2 - Set up the domain for offline storage
        ChatDomain domain = new ChatDomain.Builder(this, chatClient)
                // Enable offline support
                .offlineEnabled()
                .build();

        ChatUI ui = new ChatUI.Builder(this).build();
        User user = new User();
        user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.getExtraData().put("name", Provider.getInstance().getUser_name());
        user.getExtraData().put("image", Provider.getInstance().getProfile_pic_url());

        chatClient.connectUser(user, Constants.STREAM_API_TOKEN).enqueue((result) -> {
            if (result.isSuccess()) {
                Log.d("SuccessChatAuth", result.toString());
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                ChatActivity.super.onBackPressed();
            }

        });
    }

}