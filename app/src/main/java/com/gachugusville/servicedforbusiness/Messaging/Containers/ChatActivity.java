package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Constants;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.logger.ChatLogLevel;
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

        


    }

}