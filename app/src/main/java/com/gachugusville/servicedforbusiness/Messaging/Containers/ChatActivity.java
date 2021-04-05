package com.gachugusville.servicedforbusiness.Messaging.Containers;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    private List<ChatDialog> dialogs;
    private DialogsListAdapter<ChatDialog> dialogsListAdapter;
    private String myUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        DialogsList dialogsList = findViewById(R.id.dialogsList);

        dialogsListAdapter = new DialogsListAdapter<>(R.layout.item_dialog_custom, new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                Picasso.get().load(url).into(imageView);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) myUid = user.getUid();
        String customerUid = ""; //TODO get customer UID
        FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
        try {
            mFirebaseFirestore.collection("Providers")
                    .document(myUid)
                    .collection(customerUid)
                    //Contains documents with fields, name, dialogImageUrl, last message   AND A COLLECTION OF MESSAGES
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            Log.d("Error", Objects.requireNonNull(error.getMessage()));
                        }
                        try {
                            if (value != null) {
                                for (DocumentChange documentChange : value.getDocumentChanges()) {
                                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                        final ChatDialog chatDialog = documentChange.getDocument().toObject(ChatDialog.class);
                                        dialogs.add(chatDialog);
                                        dialogsListAdapter.notifyDataSetChanged();
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

        dialogs = new ArrayList<>();
        dialogsList.setAdapter(dialogsListAdapter);
        dialogsListAdapter.setItems(dialogs);

        dialogsListAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener<ChatDialog>() {
            @Override
            public void onDialogClick(ChatDialog dialog) {
                startActivity(new Intent(ChatActivity.this, ConversationActivity.class));
            }
        });

        dialogsListAdapter.setOnDialogLongClickListener(new DialogsListAdapter.OnDialogLongClickListener<ChatDialog>() {
            @Override
            public void onDialogLongClick(ChatDialog dialog) {

            }
        });



    }

    private void onNewMessage(String dialogId, IMessage message) {
        if (!dialogsListAdapter.updateDialogWithMessage(dialogId, message)) {
            dialogs.add(new ChatDialog());
        }
    }

}