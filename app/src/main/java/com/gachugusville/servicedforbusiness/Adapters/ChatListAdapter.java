package com.gachugusville.servicedforbusiness.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Messaging.Containers.ChatActivity;
import com.gachugusville.servicedforbusiness.Messaging.Containers.ConversationActivity;
import com.gachugusville.servicedforbusiness.Messaging.Containers.MessagesActivity;
import com.gachugusville.servicedforbusiness.Utils.ChatModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MessageHolder> {
    Context context;
    List<ChatModel> chats;

    public ChatListAdapter(Context context, List<ChatModel> chats) {
        this.context = context;
        this.chats = chats;
    }


    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_layout, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Picasso.get().load(chats.get(0).getCustomer_url()).into(holder.sender_DP);
        holder.sender_name.setText(chats.get(0).getName());
        holder.Uid = chats.get(position).getUid();
        holder.sender_distance.setText(String.format("%sAway", chats.get(0).getDistance()));
        holder.chat_layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, ConversationActivity.class);
            intent.putExtra("Uid", holder.Uid);
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder {

        LinearLayout chat_layout;
        CircleImageView sender_DP;
        String Uid;
        TextView sender_name, sender_distance;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            sender_DP = itemView.findViewById(R.id.sender_DP);
            chat_layout = itemView.findViewById(R.id.chat_layout);
            sender_name = itemView.findViewById(R.id.sender_name);
            sender_distance = itemView.findViewById(R.id.sender_distance);

        }
    }
}
