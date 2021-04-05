package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Dialog;
import com.gachugusville.servicedforbusiness.Utils.Messaging.ChatDialog;
import com.gachugusville.servicedforbusiness.Utils.ServiceCategoryList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mesibo.api.Mesibo;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    class DemoUser {
        public String token;
        public String name;
        public String address;

        DemoUser(String token, String name, String address) {
            this.token = token;
            this.name = name;
            this.address = address;
        }
    }

    //Refer to the Get-Started guide to create two users and their access tokens
    DemoUser mUser1 = new DemoUser("xyz", "User-1", "123");
    DemoUser mUser2 = new DemoUser("pqr", "User-1", "456");

    DemoUser mRemoteUser;
    Mesibo.UserProfile mProfile;
    Mesibo.ReadDbSession mReadSession;

    private List<ChatDialog> dialogs;
    private DialogsListAdapter<ChatDialog> dialogsListAdapter;
    private String myUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Mesibo api = Mesibo.getInstance();
        api.init(getApplicationContext());

    }

}