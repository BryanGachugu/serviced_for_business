package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

public class ChatActivity extends AppCompatActivity {

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>(dialogs, new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                //If you using another library - write here your way to load image
                Picasso.with(DialogsListActivity.this).load(url).into(imageView);
            }
        });

        dialogsListView.setAdapter(dialogsListAdapter);


    }

}